package com.example.sunnyweather.ui.weather

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.WindowCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.sunnyweather.R
import com.example.sunnyweather.logic.model.Weather

class WeatherActivity : AppCompatActivity() {

    //延迟初始化viewModel对象
    internal val viewModel: WeatherViewModel by viewModels {
        ViewModelProvider.NewInstanceFactory()
    }

    private val forecastLayout: LinearLayout by lazy { findViewById(R.id.forecastLayout) }
    private val windScale: TextView by lazy { findViewById(R.id.windScale) }
    private val windDir: TextView by lazy { findViewById(R.id.windDir) }
    private val humidity: TextView by lazy { findViewById(R.id.humidity) }
    private val feelsLike: TextView by lazy { findViewById(R.id.feelsLike) }
    private val currentWeather: TextView by lazy { findViewById(R.id.currentWeather) }
    private val currentTemp: TextView by lazy { findViewById(R.id.currentTemp) }
    private val locationName: TextView by lazy { findViewById(R.id.locationName) }
    private val sunTv: TextView by lazy { findViewById(R.id.sunTv) }
    private val washTv: TextView by lazy { findViewById(R.id.washTv) }
    private val sportTv: TextView by lazy { findViewById(R.id.sportTv) }
    private val swipeRefresh: SwipeRefreshLayout by lazy { findViewById(R.id.swipeRefresh) }
    private val navBtn: Button by lazy { findViewById(R.id.navBtn) }
    val drawerLayout: DrawerLayout by lazy { findViewById(R.id.drawerLayout) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        //设置状态栏沉浸
        WindowCompat.setDecorFitsSystemWindows(window, false)
        //设置标题太长触发走马灯效果
        locationName.ellipsize = TextUtils.TruncateAt.MARQUEE
        locationName.isSelected = true
        locationName.isFocusable = true
        locationName.isSingleLine = true
        locationName.isFocusableInTouchMode = true
        //从Intent中取出地区名称和ID赋值到viewModel相应变量中
        viewModel.locationName = intent.getStringExtra("location_name") ?: ""
        viewModel.locationID = intent.getStringExtra("location_id") ?: ""
        //观察liveData数据发生改变，请求回调数据不为空则解析数据并展示
        viewModel.weatherLiveData.observe(this) {
            val weather = it.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                Toast.makeText(this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show()
                it.exceptionOrNull()?.printStackTrace()
            }
            //网络请求完成取消刷新部件显示
            swipeRefresh.isRefreshing = false
        }
        //设置刷新部件颜色
        swipeRefresh.setColorSchemeResources(
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_purple
        )
        //初始化页面发起网络请求
        refreshWeather()
        swipeRefresh.setOnRefreshListener {
            //当下拉刷新页面时发起网络请求刷新天气信息
            refreshWeather()
        }
        navBtn.setOnClickListener {
            //点击标题左边按钮以打开侧滑栏界面
            drawerLayout.openDrawer(GravityCompat.START)
        }
        //监听侧滑栏状态以在关闭时隐藏输入法
        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {

            override fun onDrawerClosed(drawerView: View) {
                //当侧滑栏关闭时隐藏用户输入键盘
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(drawerView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {
            }

            override fun onDrawerStateChanged(newState: Int) {
            }
        })
    }

    /**
     * 解析天气信息数据并展示
     *
     * @param weather 封装的天气信息数据
     */
    private fun showWeatherInfo(weather: Weather) {
        //设置标题城市名
        locationName.text = viewModel.locationName
        //获取实时天气与未来天气和生活指数数据集
        val now = weather.now
        val dailyList = weather.daily
        val indicesList = weather.indices
        //填充实时天气布局中数据
        currentTemp.text = getString(R.string.real_time_temp, now.temp)
        currentWeather.text = now.text
        feelsLike.text = getString(R.string.real_time_feels_like, now.feelsLike)
        humidity.text = getString(R.string.real_time_humidity, now.humidity)
        windDir.text = now.windDir
        windScale.text = getString(R.string.real_time_wind_scale, now.windScale)
        //填充未来天气布局中数据
        //删除未来天气布局原子视图
        forecastLayout.removeAllViews()
        for (daily in dailyList) {
            //将未来天气视图填充到未来天气布局中
            val view = LayoutInflater.from(this).inflate(R.layout.item_forecast, forecastLayout, false)
            val dateInfo = view.findViewById<TextView>(R.id.dateInfo)
            val weatherInfo = view.findViewById<TextView>(R.id.weatherInfo)
            val temperatureInfo = view.findViewById<TextView>(R.id.temperatureInfo)
            val humidityInfo = view.findViewById<TextView>(R.id.humidityInfo)
            dateInfo.text = daily.fxDate
            weatherInfo.text = getString(R.string.weather_info, daily.textDay, daily.textNight)
            temperatureInfo.text = getString(R.string.temperature_info, daily.tempMax, daily.tempMin)
            humidityInfo.text = getString(R.string.real_time_humidity, daily.humidity)
            //添加自定义未来天气视图到父布局
            forecastLayout.addView(view)
        }
        //填充当天空气生活指数数据
        if (indicesList.isNotEmpty()) {
            for (indices in indicesList) {
                when (indices.type) {
                    //运动指数
                    "1" -> sportTv.text = indices.category
                    //洗车指数
                    "2" -> washTv.text = indices.category
                    //紫外线指数
                    "5" -> sunTv.text = indices.category
                }
            }
        } else {
            sportTv.text = getString(R.string.unknown)
            washTv.text = getString(R.string.unknown)
            sunTv.text = getString(R.string.unknown)
        }
    }

    /**
     * 执行刷新天气请求
     *
     */
    internal fun refreshWeather() {
        //刷新部件显示
        swipeRefresh.isRefreshing = true
        viewModel.refreshWeather(viewModel.locationID)
    }
}
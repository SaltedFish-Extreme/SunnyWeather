package com.example.sunnyweather.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.sunnyweather.R
import com.example.sunnyweather.logic.model.Weather

class WeatherActivity : AppCompatActivity() {

    //初始化viewModel对象
    private val viewModel: WeatherViewModel by viewModels {
        ViewModelProvider.NewInstanceFactory()
    }

    //懒加载要查询的地区ID
    private lateinit var locationID: String

    private val forecastLayout: LinearLayout by lazy { findViewById(R.id.forecastLayout) }
    private val windScale: TextView by lazy { findViewById(R.id.windScale) }
    private val windDir: TextView by lazy { findViewById(R.id.windDir) }
    private val humidity: TextView by lazy { findViewById(R.id.humidity) }
    private val feelsLike: TextView by lazy { findViewById(R.id.feelsLike) }
    private val currentWeather: TextView by lazy { findViewById(R.id.currentWeather) }
    private val currentTemp: TextView by lazy { findViewById(R.id.currentTemp) }
    private val locationName: TextView by lazy { findViewById(R.id.locationName) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        //从Intent中取出地区名称赋值到viewModel相应变量中
        viewModel.locationName = intent.getStringExtra("location_name") ?: ""
        //从Intent中取出地区ID初始化locationID
        locationID = intent.getStringExtra("location_id") ?: ""
        //观察liveData数据发生改变，请求回调数据不为空则解析数据并展示
        viewModel.weatherLiveData.observe(this) {
            val weather = it.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                Toast.makeText(this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show()
                it.exceptionOrNull()?.printStackTrace()
            }
        }
        //执行刷新天气请求
        viewModel.refreshWeather(locationID)
    }

    /**
     * 解析天气信息数据并展示
     *
     * @param weather 封装的天气信息数据
     */
    private fun showWeatherInfo(weather: Weather) {
        //设置标题城市名
        locationName.text = viewModel.locationName
        //获取实时天气与未来天气数据集
        val now = weather.now
        val dailyList = weather.daily
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
            val view = LayoutInflater.from(this).inflate(R.layout.item_forecast, forecastLayout)
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
    }
}
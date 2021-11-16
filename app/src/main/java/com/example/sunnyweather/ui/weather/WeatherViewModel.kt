package com.example.sunnyweather.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.sunnyweather.logic.Repository

/**
 * Created by 咸鱼至尊 on 2021/11/14
 *
 * des: 查询天气信息vm层
 */
class WeatherViewModel : ViewModel() {

    //可供观察的liveData对象
    private val locationIDLiveData = MutableLiveData<String>()

    //对界面上相关数据进行缓存
    lateinit var locationName: String
    lateinit var locationID: String

    /**
     * 观察者
     *
     * 当数据发生改变时调用仓库层的刷新天气信息方法
     *
     * 将返回的liveData对象转换成可供观察的liveData对象
     */
    val weatherLiveData = Transformations.switchMap(locationIDLiveData) {
        Repository.refreshWeather(it)
    }

    /**
     * 刷新天气信息
     *
     * @param locationID 要搜索的城市ID
     */
    fun refreshWeather(locationID: String) {
        locationIDLiveData.value = locationID
    }
}
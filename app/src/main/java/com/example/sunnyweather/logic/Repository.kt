package com.example.sunnyweather.logic

import androidx.lifecycle.liveData
import com.example.sunnyweather.logic.dao.LocationDao
import com.example.sunnyweather.logic.model.LocationResponse
import com.example.sunnyweather.logic.model.Weather
import com.example.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

/**
 * Created by 咸鱼至尊 on 2021/11/13
 *
 * des: 仓库层统一封装入口
 */
object Repository {
    /**
     * 搜索城市数据
     *
     * @param location 要搜索的城市
     * @return 返回数据
     */
    fun searchLocation(location: String) = fire {
        //发起请求获取响应数据
        val locationResponse = SunnyWeatherNetwork.searchLocation(location)
        if (locationResponse.code == "200") {
            //请求成功封装返回的城市数据列表
            val locations = locationResponse.location
            Result.success(locations)
        } else {
            //请求失败封装返回码打印异常
            Result.failure(RuntimeException("response status is ${locationResponse.code}"))
        }
    }

    /**
     * 刷新天气信息
     *
     * 封装实时天气信息和未来天气信息两次调用请求
     *
     * @param locationID 要搜索的城市ID
     * @return 返回数据
     */
    fun refreshWeather(locationID: String) = fire {
        //创建协程作用域，以使用async函数
        coroutineScope {
            //并发执行获取实时天气信息与未来天气信息和当天生活指数请求，提升程序运行效率
            val deferredNow = async {
                SunnyWeatherNetwork.getNowWeather(locationID)
            }
            val deferredDaily = async {
                SunnyWeatherNetwork.getDailyWeather(locationID)
            }
            val deferredIndices = async {
                SunnyWeatherNetwork.getIndicesWeather(locationID)
            }
            //分别获取响应数据
            val nowResponse = deferredNow.await()
            val dailyResponse = deferredDaily.await()
            val indicesResponse = deferredIndices.await()
            if (nowResponse.code == "200" && dailyResponse.code == "200" && indicesResponse.code == "200") {
                //如果全部请求成功则将三者信息封装返回
                val weather = Weather(nowResponse.now, dailyResponse.daily, indicesResponse.daily)
                Result.success(weather)
            } else if (nowResponse.code == "200" && dailyResponse.code == "200") {
                //如果实时及未来天气信息请求成功则将前两者信息封装返回 (对于没有生活指数的城市,返回代码204)
                val weather = Weather(nowResponse.now, dailyResponse.daily, emptyList())
                Result.success(weather)
            } else {
                //请求失败则封装三者返回码打印异常
                Result.failure(RuntimeException("now response code is ${nowResponse.code}, daily response code is ${dailyResponse.code}, indices response code is ${indicesResponse.code}"))
            }
        }
    }

    /**
     * 入口函数 简化liveData函数用法
     *
     * 封装捕获异常代码块以及返回包装数据
     *
     * 函数类型声明suspend以表示传入的lambda表达式的代码也拥有挂起函数上下文
     *
     * @param T liveData类型
     * @param block 执行lambda表达式代码
     * @return 返回包装结果
     */
    private fun <T> fire(block: suspend () -> Result<T>) = liveData(Dispatchers.IO) {
        val result = try {
            block()
        } catch (e: Exception) {
            Result.failure(e)
        }
        //返回包装结果
        emit(result)
    }

    /**
     * 存储城市对象到sp缓存
     *
     * @param location 城市对象
     */
    fun saveLocation(location: LocationResponse.Location) = LocationDao.saveLocation(location)

    /**
     * 从sp缓存获取城市对象
     *
     * @return 城市对象
     */
    fun getSavedLocation() = LocationDao.getSavedLocation()

    /**
     * 判断城市对象是否已被缓存
     *
     * @return 是否
     */
    fun isLocationSaved() = LocationDao.isLocationSaved()
}
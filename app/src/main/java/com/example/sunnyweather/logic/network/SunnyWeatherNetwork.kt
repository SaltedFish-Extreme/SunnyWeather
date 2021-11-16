package com.example.sunnyweather.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Created by 咸鱼至尊 on 2021/11/13
 *
 * des: 网络数据源访问入口
 */
object SunnyWeatherNetwork {

    //创建LocationService接口动态代理对象
    private val locationService = ServiceCreator.createCity<LocationService>()

    //创建WeatherService接口动态代理对象
    private val weatherService = ServiceCreator.createWeather<WeatherService>()

    /**
     * 发起搜索城市数据请求
     *
     * @param location 要搜索的城市名
     * @return 返回数据
     */
    suspend fun searchLocation(location: String) = locationService.searchLocations(location).await()

    /**
     * 发起实时天气查询请求
     *
     * @param locationID 要查询的城市ID
     * @return 返回数据
     */
    suspend fun getNowWeather(locationID: String) = weatherService.getNowWeather(locationID).await()

    /**
     * 发起未来一周天气查询请求
     *
     * @param locationID 要查询的城市ID
     * @return 返回数据
     */
    suspend fun getDailyWeather(locationID: String) = weatherService.getDailyWeather(locationID).await()

    /**
     * 发起当天空气生活指数查询请求
     *
     * @param locationID 要查询的城市ID
     * @return 返回数据
     */
    suspend fun getIndicesWeather(locationID: String) = weatherService.getIndicesWeather(locationID).await()

    /**
     * 简化Retrofit网络请求回调写法
     *
     * @param T 网络请求接口泛型
     * @return 结果回调
     */
    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine {
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) it.resume(body)
                    else it.resumeWithException(RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    it.resumeWithException(t)
                }
            })
        }
    }
}
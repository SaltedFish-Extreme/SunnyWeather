package com.example.sunnyweather.logic.network

import com.example.sunnyweather.SunnyWeatherApplication
import com.example.sunnyweather.logic.model.DailyResponse
import com.example.sunnyweather.logic.model.NowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by 咸鱼至尊 on 2021/11/13
 *
 * des: 天气查询网络请求接口
 */
interface WeatherService {
    /**
     * 实时天气查询
     *
     * @param locationID 要查询的城市ID
     * @return 实时天气信息请求回调
     */
    @GET("v7/weather/now?key=${SunnyWeatherApplication.TOKEN}")
    fun getNowWeather(@Query("location") locationID: String): Call<NowResponse>

    /**
     * 未来一周天气查询
     *
     * @param locationID 要查询的城市ID
     * @return 未来一周天气信息请求回调
     */
    @GET("v7/weather/7d?key=${SunnyWeatherApplication.TOKEN}")
    fun getDailyWeather(@Query("location") locationID: String): Call<DailyResponse>
}
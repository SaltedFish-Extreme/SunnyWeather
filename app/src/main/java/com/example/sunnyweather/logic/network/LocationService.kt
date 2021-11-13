package com.example.sunnyweather.logic.network

import com.example.sunnyweather.SunnyWeatherApplication
import com.example.sunnyweather.logic.model.LocationResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by 咸鱼至尊 on 2021/11/12
 *
 * des: 城市搜索网络请求接口
 */
interface LocationService {
    /**
     * 城市搜索
     *
     * @param location 要搜索的城市
     * @return 城市信息网络请求回调
     */
    @GET("v2/city/lookup?key=${SunnyWeatherApplication.TOKEN}")
    fun searchLocations(@Query("location") location: String): Call<LocationResponse>
}
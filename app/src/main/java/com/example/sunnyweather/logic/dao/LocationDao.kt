package com.example.sunnyweather.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.example.sunnyweather.SunnyWeatherApplication
import com.example.sunnyweather.logic.model.LocationResponse
import com.google.gson.Gson

/**
 * Created by 咸鱼至尊 on 2021/11/16
 *
 * des: sp缓存城市记录
 */
object LocationDao {

    /**
     * 存储数据类对象到sp缓存
     *
     * @param location 数据类对象
     */
    fun saveLocation(location: LocationResponse.Location) {
        sharedPreferences().edit {
            putString("location", Gson().toJson(location))
        }
    }

    /**
     * 从sp缓存中获取数据类对象
     *
     * @return 数据类对象
     */
    fun getSavedLocation(): LocationResponse.Location {
        val locationJson = sharedPreferences().getString("location", "")
        return Gson().fromJson(locationJson, LocationResponse.Location::class.java)
    }

    /**
     * 判断数据是否已被存储
     *
     * @return 是否
     */
    fun isLocationSaved() = sharedPreferences().contains("location")

    /**
     * 获取sp对象
     *
     * @return sp对象
     */
    private fun sharedPreferences() = SunnyWeatherApplication.context.getSharedPreferences("sunny_weather", Context.MODE_PRIVATE)
}
package com.example.sunnyweather.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by 咸鱼至尊 on 2021/11/12
 *
 * des: Retrofit构建器
 */
object ServiceCreator {
    //城市查询全局URL根路径
    private const val BASE_CITY_URL = "https://geoapi.qweather.com/"

    //天气全局URL根路径
    private const val BASE_WEATHER_URL = "https://devapi.qweather.com/"

    //创建城市Retrofit对象并指定请求根目录及转换器
    private val retrofit_city = Retrofit.Builder()
        .baseUrl(BASE_CITY_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //创建天气Retrofit对象并指定请求根目录及转换器
    private val retrofit_weather = Retrofit.Builder()
        .baseUrl(BASE_WEATHER_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * 创建城市网络请求接口动态代理对象
     *
     * @param T 网络请求接口对象类型
     * @param serviceClass 网络请求接口对象
     * @return 动态代理对象
     */
    fun <T> createCity(serviceClass: Class<T>): T = retrofit_city.create(serviceClass)

    /**
     * 简化获取城市网络请求接口动态代理对象写法
     *
     * @param T 网络请求接口对象类型
     * @return 动态代理对象
     */
    inline fun <reified T> createCity(): T = createCity(T::class.java)

    /**
     * 创建天气网络请求接口动态代理对象
     *
     * @param T 网络请求接口对象类型
     * @param serviceClass 网络请求接口对象
     * @return 动态代理对象
     */
    fun <T> createWeather(serviceClass: Class<T>): T = retrofit_weather.create(serviceClass)

    /**
     * 简化获取天气网络请求接口动态代理对象写法
     *
     * @param T 网络请求接口对象类型
     * @return 动态代理对象
     */
    inline fun <reified T> createWeather(): T = createWeather(T::class.java)
}
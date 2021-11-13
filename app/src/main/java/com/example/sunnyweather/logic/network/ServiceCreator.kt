package com.example.sunnyweather.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by 咸鱼至尊 on 2021/11/12
 *
 * des: Retrofit构建器
 */
object ServiceCreator {
    //全局URL根路径
    private const val BASE_URL = "https://geoapi.qweather.com/"

    //创建Retrofit对象并指定请求根目录及转换器
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * 创建网络请求接口动态代理对象
     *
     * @param T 网络请求接口对象类型
     * @param serviceClass 网络请求接口对象
     * @return 动态代理对象
     */
    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    /**
     * 简化获取网络请求接口动态代理对象写法
     *
     * @param T 网络请求接口对象类型
     * @return 动态代理对象
     */
    inline fun <reified T> create(): T = create(T::class.java)
}
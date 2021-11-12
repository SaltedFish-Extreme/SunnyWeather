package com.example.sunnyweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * Created by 咸鱼至尊 on 2021/11/12
 *
 * des: 全局变量及常量声明
 */
class SunnyWeatherApplication : Application() {
    companion object {
        //全局context对象
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        //和风天气token
        const val TOKEN = "54e9fb5257254ae5b06288385a754bdc"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}
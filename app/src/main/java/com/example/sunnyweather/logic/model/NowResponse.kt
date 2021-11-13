package com.example.sunnyweather.logic.model

/**
 * Created by 咸鱼至尊 on 2021/11/13
 *
 * des: 实时天气信息数据类
 */
/**
 * 实时天气信息
 *
 * @property code 返回码
 * @property now 天气信息
 */
data class NowResponse(val code: String, val now: Now) {
    /**
     * 天气信息
     *
     * @property temp 气温
     * @property feelsLike 体感温度
     * @property humidity 相对湿度
     * @property text 天气状况
     * @property windDir 风向
     * @property windScale 风力
     */
    data class Now(val temp: String, val feelsLike: String, val humidity: String, val text: String, val windDir: String, val windScale: String)
}
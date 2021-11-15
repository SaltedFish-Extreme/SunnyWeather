package com.example.sunnyweather.logic.model

/**
 * Created by 咸鱼至尊 on 2021/11/13
 *
 * des: 未来一周天气天气信息数据类
 */
/**
 * 未来一周天气信息
 *
 * @property code 返回码
 * @property daily 逐天天气信息
 */
data class DailyResponse(val code: String, val daily: List<Daily>) {
    /**
     * 逐天天气信息
     *
     * @property fxDate 日期
     * @property tempMax 最高温
     * @property tempMin 最低温
     * @property textDay 白天天气状况
     * @property textNight 夜间天气状况
     * @property humidity 相对湿度
     */
    data class Daily(
        val fxDate: String,
        val tempMax: String,
        val tempMin: String,
        val textDay: String,
        val textNight: String,
        val humidity: String
    )
}
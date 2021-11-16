package com.example.sunnyweather.logic.model

/**
 * Created by 咸鱼至尊 on 2021/11/13
 *
 * des: 天气封装类
 */
/**
 * 封装实时及未来一周天气和当天天气生活指数数据类
 *
 * @property now 实时天气
 * @property daily 未来一周天气
 * @property indices 当天空气生活指数
 */
data class Weather(val now: NowResponse.Now, val daily: List<DailyResponse.Daily>, val indices: List<IndicesResponse.Daily>)
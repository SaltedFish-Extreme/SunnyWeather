package com.example.sunnyweather.logic.model

/**
 * Created by 咸鱼至尊 on 2021/11/12
 *
 * des: 城市信息数据类
 */

/**
 * 用于保存返回城市信息的数据类
 *
 * @property code 响应码
 * @property location 多个城市信息的集合
 */
data class LocationResponse(val code: String, val location: List<Location>)

/**
 * 城市信息
 *
 * @property name 查询到的城市名
 * @property id 用于查询天气的城市id
 * @property country 国
 * @property adm1 省
 * @property adm2 市
 */
data class Location(val name: String, val id: String, val country: String, val adm1: String, val adm2: String)
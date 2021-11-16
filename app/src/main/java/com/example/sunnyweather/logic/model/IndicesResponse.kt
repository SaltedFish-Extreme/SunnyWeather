package com.example.sunnyweather.logic.model

/**
 * Created by 咸鱼至尊 on 2021/11/16
 *
 * des: 生活指数数据类
 */
/**
 * 当天天气生活指数
 *
 * @property code 返回码
 * @property daily 当天生活指数
 */
data class IndicesResponse(val code: String, val daily: List<Daily>) {
    /**
     * 生活指数
     *
     * @property type 指数类型
     * @property name 指数名称
     * @property category 指数状态
     */
    data class Daily(val type: String, val name: String, val category: String)
}
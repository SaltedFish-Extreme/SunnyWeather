package com.example.sunnyweather.logic

import androidx.lifecycle.liveData
import com.example.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers

/**
 * Created by 咸鱼至尊 on 2021/11/13
 *
 * des: 仓库层统一封装入口
 */
object Repository {
    /**
     * 搜索城市数据
     *
     * @param location 要搜索的城市
     * @return 返回数据
     */
    fun searchLocation(location: String) = liveData(Dispatchers.IO) {
        val result = try {
            //发起请求获取响应数据
            val locationResponse = SunnyWeatherNetwork.searchLocation(location)
            if (locationResponse.code == "200") {
                //请求成功封装返回的城市数据列表
                val locations = locationResponse.location
                Result.success(locations)
            } else {
                //请求失败封装异常信息
                Result.failure(RuntimeException("response status is ${locationResponse.code}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
        //返回liveData对象
        emit(result)
    }
}
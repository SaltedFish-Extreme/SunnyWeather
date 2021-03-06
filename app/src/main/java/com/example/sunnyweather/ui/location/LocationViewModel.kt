package com.example.sunnyweather.ui.location

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.sunnyweather.logic.Repository
import com.example.sunnyweather.logic.model.LocationResponse.Location

/**
 * Created by 咸鱼至尊 on 2021/11/13
 *
 * des: 搜索城市数据vm层
 */
class LocationViewModel : ViewModel() {

    //可供观察的liveData对象
    private val searchLiveData = MutableLiveData<String>()

    //对界面上显示的城市数据进行缓存
    val locationList = ArrayList<Location>()

    /**
     * 观察者
     *
     * 当数据发生改变时调用仓库层的搜索城市数据方法
     *
     * 将返回的liveData对象转换成可供观察的liveData对象
     */
    val locationLiveData = Transformations.switchMap(searchLiveData) {
        Repository.searchLocation(it)
    }

    /**
     * 搜索城市数据
     *
     * @param location 要搜索的城市名
     */
    fun searchLocation(location: String) {
        searchLiveData.value = location
    }

    /**
     * 存储城市对象到sp缓存
     *
     * @param location 城市对象
     */
    fun saveLocation(location: Location) = Repository.saveLocation(location)

    /**
     * 从sp缓存获取城市对象
     *
     * @return 城市对象
     */
    fun getSaveLocation() = Repository.getSavedLocation()

    /**
     * 判断城市对象是否已被缓存
     *
     * @return 是否
     */
    fun isLocationSaved() = Repository.isLocationSaved()
}
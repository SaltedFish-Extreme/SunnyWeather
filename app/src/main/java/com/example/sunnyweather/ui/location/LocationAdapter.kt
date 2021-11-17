package com.example.sunnyweather.ui.location

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sunnyweather.R
import com.example.sunnyweather.logic.model.LocationResponse.Location
import com.example.sunnyweather.ui.weather.WeatherActivity

/**
 * Created by 咸鱼至尊 on 2021/11/13
 */
class LocationAdapter(private val fragment: LocationFragment, private val locationList: List<Location>) :
    RecyclerView.Adapter<LocationAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val locationAddress: TextView = view.findViewById(R.id.locationAddress)
        val locationName: TextView = view.findViewById(R.id.locationName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_location, parent, false)
        ViewHolder(view).apply {
            //itemView父布局添加点击事件，以跳转天气界面
            itemView.setOnClickListener {
                val location = locationList[adapterPosition]
                val activity = fragment.activity
                //判断当前适配器所属fragment是否属于WeatherActivity
                if (activity is WeatherActivity) {
                    //关闭侧滑栏
                    activity.drawerLayout.closeDrawers()
                    //给WeatherActivity的viewModel对象设置属性
                    activity.viewModel.locationID = location.id
                    activity.viewModel.locationName = location.name
                    //刷新天气信息
                    activity.refreshWeather()
                } else {
                    Intent(parent.context, WeatherActivity::class.java).apply {
                        //传递地区名称以及地区id启动天气页面
                        putExtra("location_name", location.name)
                        putExtra("location_id", location.id)
                        fragment.startActivity(this)
                    }
                    //关闭所属页面(主页)
                    activity?.finish()
                }
                //最后都将城市信息存到sp缓存中
                fragment.viewModel.value.saveLocation(location)
            }
            return this
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val location = locationList[position]
        //设置子项itemView的地区名和省市区
        holder.locationName.text = location.name
        holder.locationAddress.text = fragment.getString(R.string.location_address, location.country, location.adm1, location.adm2)
    }

    override fun getItemCount() = locationList.size
}
package com.example.sunnyweather.ui.location

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.sunnyweather.R
import com.example.sunnyweather.logic.model.Location
import com.example.sunnyweather.ui.weather.WeatherActivity

/**
 * Created by 咸鱼至尊 on 2021/11/13
 */
class LocationAdapter(private val fragment: Fragment, private val locationList: List<Location>) : RecyclerView.Adapter<LocationAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val locationAddress: TextView = view.findViewById(R.id.locationAddress)
        val locationName: TextView = view.findViewById(R.id.locationName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_location, parent, false)
        ViewHolder(view).apply {
            itemView.setOnClickListener {
                val location = locationList[adapterPosition]
                Intent(parent.context, WeatherActivity::class.java).apply {
                    putExtra("location_name", location.name)
                    putExtra("location_id", location.id)
                    fragment.startActivity(this)
                }
            }
            return this
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val location = locationList[position]
        holder.locationName.text = location.name
        holder.locationAddress.text = fragment.getString(R.string.location_address, location.country, location.adm1, location.adm2)
    }

    override fun getItemCount() = locationList.size
}
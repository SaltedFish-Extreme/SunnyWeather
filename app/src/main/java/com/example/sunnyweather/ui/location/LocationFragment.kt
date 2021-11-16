package com.example.sunnyweather.ui.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sunnyweather.R

/**
 * Created by 咸鱼至尊 on 2021/11/13
 */
class LocationFragment : Fragment() {

    //延迟初始化viewModel对象
    private val viewModel = viewModels<LocationViewModel> {
        ViewModelProvider.NewInstanceFactory()
    }

    //延迟初始化recyclerView适配器
    private lateinit var adapter: LocationAdapter

    private lateinit var recyclerView: RecyclerView
    private lateinit var actionBarLayout: FrameLayout
    private lateinit var searchLocationEdit: EditText
    private lateinit var bgImageView: ImageView
    private lateinit var mRootView: View

    //控件绑定
    private fun initViews(view: View) {
        bgImageView = view.findViewById(R.id.bgImageView)
        searchLocationEdit = view.findViewById(R.id.searchLocationEdit)
        actionBarLayout = view.findViewById(R.id.actionBarLayout)
        recyclerView = view.findViewById(R.id.recyclerView)
    }

    //绑定布局资源
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mRootView = inflater.inflate(R.layout.fragment_location, container, false)
        initViews(mRootView)
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        //设置recyclerView布局管理器
        recyclerView.layoutManager = layoutManager
        adapter = LocationAdapter(this, viewModel.value.locationList)
        //设置recyclerView适配器
        recyclerView.adapter = adapter
        searchLocationEdit.addTextChangedListener {
            //监听当输入框文字发生改变且不为空时发送请求搜索城市数据
            val content = it.toString()
            if (content.isNotEmpty()) {
                viewModel.value.searchLocation(content)
            } else {
                recyclerView.visibility = View.INVISIBLE
                bgImageView.visibility = View.VISIBLE
                viewModel.value.locationList.clear()
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.value.locationLiveData.observe(viewLifecycleOwner) {
            //观察liveData数据发生改变，请求回调数据不为空则添加进数据源集合并刷新页面
            val locations = it.getOrNull()
            if (locations != null) {
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.INVISIBLE
                viewModel.value.locationList.clear()
                viewModel.value.locationList.addAll(locations)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "未能查询到任何城市", Toast.LENGTH_SHORT).show()
                it.exceptionOrNull()?.printStackTrace()
            }
        }
    }
}
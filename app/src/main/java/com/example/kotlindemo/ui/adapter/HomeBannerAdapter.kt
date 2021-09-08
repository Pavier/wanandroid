package com.example.kotlindemo.ui.adapter

import android.view.View
import com.example.kotlindemo.R
import com.example.kotlindemo.data.bean.Banner
import com.example.kotlindemo.ui.adapter.viewholder.HomeBannerViewHolder
import com.zhpan.bannerview.BaseBannerAdapter

/**
 * @author Created by pw
 * @description:
 * @date:2021/8/28 17:37
 */
class HomeBannerAdapter : BaseBannerAdapter<Banner,HomeBannerViewHolder>() {
    override fun createViewHolder(itemView: View, viewType: Int): HomeBannerViewHolder {
        return HomeBannerViewHolder(itemView)
    }

    override fun onBind(
        holder: HomeBannerViewHolder?,
        data: Banner?,
        position: Int,
        pageSize: Int
    ) {
        holder?.bindData(data,position,pageSize)
    }

    override fun getLayoutId(viewType: Int): Int = R.layout.banner_itemhome
}
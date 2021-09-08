package com.example.kotlindemo.ui.adapter.viewholder

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.kotlindemo.R
import com.example.kotlindemo.data.bean.Banner
import com.zhpan.bannerview.BaseViewHolder
import me.hgj.jetpackmvvm.base.appContext

/**
 * @author Created by pw
 * @description:
 * @date:2021/8/28 17:38
 */
class HomeBannerViewHolder(view: View) : BaseViewHolder<Banner>(view) {
    override fun bindData(data: Banner?, position: Int, pageSize: Int) {
        val imageView = itemView.findViewById<ImageView>(R.id.bannerhome_img)
        data?.let {
            Glide.with(appContext)
                .load(it.imagePath)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(imageView)
        }
    }
}
package com.example.kotlindemo.data.bindadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * @author Created by pw
 * @description:
 * @date:2021/9/7 16:59
 */
object CustomBindAdapter {

    @BindingAdapter(value = ["circleImageUrl"])
    @JvmStatic
    fun circleImageUrl(view :ImageView,url : String){
        Glide.with(view.context.applicationContext)
            .load(url)
            .into(view)
    }
}
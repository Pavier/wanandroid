package com.example.kotlindemo.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.kotlindemo.R
import com.example.kotlindemo.data.bean.Children
import com.example.kotlindemo.data.bean.HotKey
import com.example.kotlindemo.ext.setAdapterAnimation
import com.example.kotlindemo.utils.ColorUtil

/**
 * @author Created by pw
 * @description:
 * @date:2021/9/6 17:49
 */
class HotKeyAdapter(data : ArrayList<HotKey>) : BaseQuickAdapter<HotKey,BaseViewHolder>(R.layout.item_childer,data) {

    init {
        setAdapterAnimation(2)
    }

    override fun convert(holder: BaseViewHolder, item: HotKey) {
        item.apply {
            holder.setText(R.id.text,name)
            holder.setTextColor(R.id.text,ColorUtil.randomColor())
        }
    }
}
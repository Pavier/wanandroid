package com.example.kotlindemo.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.kotlindemo.R
import com.example.kotlindemo.ext.setAdapterAnimation

/**
 * @author Created by pw
 * @description:
 * @date:2021/9/8 11:46
 */
class HistoryAdapter(data : MutableList<String>) : BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_history,data) {

    init {
        setAdapterAnimation(2)
    }

    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.text,item)
    }
}
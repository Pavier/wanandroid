package com.example.kotlindemo.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.kotlindemo.R
import com.example.kotlindemo.data.bean.Article
import com.example.kotlindemo.data.bean.Children
import com.example.kotlindemo.utils.ColorUtil

/**
 * @author Created by pw
 * @description:
 * @date:2021/9/6 17:49
 */
class NavDetailAdapter(data : ArrayList<Article>) : BaseQuickAdapter<Article,BaseViewHolder>(R.layout.item_childer,data) {

    override fun convert(holder: BaseViewHolder, item: Article) {
        item.apply {
            holder.setText(R.id.text,title)
            holder.setTextColor(R.id.text,ColorUtil.randomColor())
        }
    }
}
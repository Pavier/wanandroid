package com.example.kotlindemo.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.kotlindemo.R
import com.example.kotlindemo.data.bean.Article
import com.example.kotlindemo.data.bean.Children
import com.example.kotlindemo.data.bean.NavData
import com.example.kotlindemo.data.bean.TreeData
import com.example.kotlindemo.ext.init
import com.example.kotlindemo.ext.setAdapterAnimation
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

/**
 * @author Created by pw
 * @description:
 * @date:2021/9/6 16:51
 */
class NavAdapter(data: MutableList<NavData>) : BaseQuickAdapter<NavData, BaseViewHolder>(R.layout.item_tree,data) {

    private var action : (data: NavData,view : View,position : Int) -> Unit = {data,view,position ->}

    init {
        setAdapterAnimation(2)

    }

    override fun convert(holder: BaseViewHolder, item: NavData) {
        item.apply {
            holder.setText(R.id.title,name)
            val recyclerView = holder.getView<RecyclerView>(R.id.recyclerView)

            recyclerView.run {
                layoutManager = FlexboxLayoutManager(context).apply {
                    flexDirection = FlexDirection.ROW
                    flexWrap = FlexWrap.WRAP
                    justifyContent = JustifyContent.FLEX_START
                }
                setHasFixedSize(true)
                setItemViewCacheSize(200)
                isNestedScrollingEnabled = false
                adapter = NavDetailAdapter(articles as ArrayList<Article>).apply {
                    setOnItemClickListener { _, view, position ->
                        action.invoke(item,view,position)
                    }
                }
            }
        }
    }

    fun setItemClick(action : (data : NavData,view: View,position : Int) -> Unit){
        this.action = action
    }
}
package com.example.kotlindemo.ui.adapter

import android.text.Html
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.kotlindemo.R
import com.example.kotlindemo.data.bean.Article
import com.example.kotlindemo.ext.setAdapterAnimation
import me.hgj.jetpackmvvm.ext.util.toHtml

/**
 * @author Created by pw
 * @description:
 * @date:2021/8/27 15:36
 */
class ProjectAdapter(data: MutableList<Article>?) : BaseQuickAdapter<Article,BaseViewHolder>(R.layout.item_article,data){
    init {
        setAdapterAnimation(2)
    }

    override fun convert(holder: BaseViewHolder, item: Article) {
        item.apply {
            holder.setText(R.id.item_home_author, "${author}${shareUser}".toHtml())
            holder.setText(R.id.item_home_date,niceDate)
            holder.setText(R.id.item_home_content,title.toHtml())
            holder.setText(R.id.item_home_type2,"${superChapterName}·${chapterName}".toHtml())

            holder.setGone(R.id.item_home_top,type != 1)
            holder.setGone(R.id.item_home_new,!fresh)
            if(tags.isNotEmpty()){
                holder.setGone(R.id.item_home_type1,false)
                holder.setText(R.id.item_home_type1, tags[0].name)
            }else{
                holder.setGone(R.id.item_home_type1,true)
            }
            if(envelopePic.isNotEmpty()){
                holder.setGone(R.id.img,false)
                holder.setGone(R.id.item_home_desc,false)
                holder.setText(R.id.item_home_desc,desc.toHtml())
                Glide.with(context)
                    .load(envelopePic)
                    .into(holder.getView(R.id.img))
            }else{
                holder.setGone(R.id.img,true)
                holder.setGone(R.id.item_home_desc,true)
            }
        }
    }
}
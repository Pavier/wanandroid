package com.example.kotlindemo.viewmodel.state

import com.example.kotlindemo.data.bean.Article
import com.example.kotlindemo.data.bean.NavData
import com.example.kotlindemo.data.bean.TreeData
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * @author Created by pw
 * @description:
 * @date:2021/9/7 10:04
 */
class TreeArticleViewModel : BaseViewModel() {
    lateinit var treeData: TreeData
    var position : Int = 0
}
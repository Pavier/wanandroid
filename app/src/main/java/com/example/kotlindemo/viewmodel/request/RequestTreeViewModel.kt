package com.example.kotlindemo.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.example.kotlindemo.api.NetWorkApi
import com.example.kotlindemo.data.bean.NavData
import com.example.kotlindemo.data.bean.TreeData
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.request
import me.hgj.jetpackmvvm.state.ResultState

/**
 * @author Created by pw
 * @description:
 * @date:2021/9/6 13:34
 */
class RequestTreeViewModel : BaseViewModel() {

    var treeResult : MutableLiveData<ResultState<ArrayList<TreeData>>> = MutableLiveData()
    var navResult : MutableLiveData<ResultState<ArrayList<NavData>>> = MutableLiveData()

    fun getTreeData(){
        request({NetWorkApi.service.getTreeData()},treeResult)
    }

    fun getNavData(){
        request({NetWorkApi.service.getNav()},navResult)
    }
}
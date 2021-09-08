package com.example.kotlindemo.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.example.kotlindemo.api.NetWorkApi
import com.example.kotlindemo.data.bean.ProjectTree
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.request
import me.hgj.jetpackmvvm.state.ResultState

/**
 * @author Created by pw
 * @description:
 * @date:2021/8/30 11:04
 */
class RequestProjectTreeViewModel : BaseViewModel() {

    val projectTreeResult : MutableLiveData<ResultState<ArrayList<ProjectTree>>> = MutableLiveData()

    fun getProjectTree(){
        request(
            {NetWorkApi.service.getProjectTree()}
            ,projectTreeResult)
    }
}
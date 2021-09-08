package com.example.kotlindemo.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.example.kotlindemo.api.NetWorkApi
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.request
import me.hgj.jetpackmvvm.state.ResultState

/**
 * @author Created by pw
 * @description:
 * @date:2021/9/7 18:19
 */
class RequestMeViewModel : BaseViewModel() {

    val result : MutableLiveData<ResultState<Any?>> = MutableLiveData()

    fun logout(){
        request({
            NetWorkApi.service.logout()
        },result)
    }
}
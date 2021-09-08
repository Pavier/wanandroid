package com.example.kotlindemo.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.example.kotlindemo.api.NetWorkApi
import com.example.kotlindemo.data.bean.UserInfo
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.request
import me.hgj.jetpackmvvm.state.ResultState

/**
 * @author Created by pw
 * @description:
 * @date:2021/8/10 15:45
 */
class RequestLoginViewModel : BaseViewModel() {

    //方式1  自动脱壳过滤处理请求结果，判断结果是否成功
    var loginResult = MutableLiveData<ResultState<UserInfo>>()

    fun login(username:String ,password : String){

        request({NetWorkApi.service.login(username,password)},loginResult,true)
    }
}
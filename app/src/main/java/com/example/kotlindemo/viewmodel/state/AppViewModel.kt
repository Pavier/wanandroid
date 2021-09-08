package com.example.kotlindemo.viewmodel.state

import com.example.kotlindemo.data.bean.UserInfo
import com.example.kotlindemo.utils.CacheUtil
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * @author Created by pw
 * @description:
 * @date:2021/9/7 16:28
 */
class AppViewModel : BaseViewModel() {

    //App的账户信息
    var userInfo = UnPeekLiveData.Builder<UserInfo>().setAllowNullValue(true).create()

    init {
        userInfo.value = CacheUtil.getUser()
    }
}
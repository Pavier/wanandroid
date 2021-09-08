package com.example.kotlindemo.viewmodel.state

import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.callback.databind.StringObservableField

/**
 * @author Created by pw
 * @description:
 * @date:2021/8/10 11:14
 */
class LoginViewModel : BaseViewModel(){

    var username = StringObservableField()

    var passwrod = StringObservableField()

    var userinfo = StringObservableField()



}
package com.example.kotlindemo.app

import com.example.kotlindemo.viewmodel.state.AppViewModel
import com.example.kotlindemo.weight.loadCallBack.EmptyCallback
import com.example.kotlindemo.weight.loadCallBack.ErrorCallback
import com.example.kotlindemo.weight.loadCallBack.LoadingCallback
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadSir
import com.tencent.mmkv.MMKV
import me.hgj.jetpackmvvm.base.BaseApp
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.AutoSizeConfig

/**
 * @author Created by pw
 * @description:
 * @date:2021/8/27 16:08
 */

// 全局配置，存放用户信息 及其他一些配置等
val appViewModel: AppViewModel by lazy{ MyApp.appViewModelInstance }

class MyApp : BaseApp() {

    companion object{
        lateinit var appViewModelInstance: AppViewModel
    }

    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(applicationContext)
        appViewModelInstance = getAppViewModelProvider().get(AppViewModel::class.java)
        //界面加载管理 初始化
        LoadSir.beginBuilder()
            .addCallback(LoadingCallback())//加载
            .addCallback(ErrorCallback())//错误
            .addCallback(EmptyCallback())//空
            .setDefaultCallback(SuccessCallback::class.java)//设置默认加载状态页
            .commit()

        initAutoSize()
    }

    private fun initAutoSize() {
        AutoSize.initCompatMultiProcess(applicationContext)
    }
}
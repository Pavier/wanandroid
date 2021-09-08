package com.example.kotlindemo.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import me.hgj.jetpackmvvm.base.fragment.BaseVmDbFragment
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * @author Created by pw
 * @description:
 * @date:2021/8/10 10:42
 */
abstract class BaseFragment<VM:BaseViewModel,DB:ViewDataBinding> : BaseVmDbFragment<VM,DB>() {

    /**
     * 当前Fragment绑定的视图布局
     */
    abstract override fun layoutId(): Int


    abstract override fun initView(savedInstanceState: Bundle?)

    /**
     * 懒加载 只有当前fragment视图显示时才会触发该方法
     */
    override fun lazyLoadData() {}

    /**
     * 创建liveData数据观察 懒加载之后才会触发
     */
    override fun createObserver(){

    }

    /**
     * Fragment执行onViewCreated后触发的方法
     */
    override fun initData() {

    }

    /**
     * 打开等待框 在这里实现你的等待框展示
     */
    override fun showLoading(message: String) {
    }

    /**
     * 关闭等待框 在这里实现你的等待框关闭
     */
    override fun dismissLoading() {
    }
}
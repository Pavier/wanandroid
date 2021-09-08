package com.example.kotlindemo.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.databinding.ViewDataBinding
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import me.hgj.jetpackmvvm.base.activity.BaseVmDbActivity
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * @author Created by pw
 * @description:
 * @date:2021/8/27 10:33
 */
abstract class BaseActivity<VM:BaseViewModel,DB:ViewDataBinding> : BaseVmDbActivity<VM,DB>(){


    override fun createObserver() {
    }

    override fun showLoading(message: String) {
    }


    override fun dismissLoading() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QMUIStatusBarHelper.translucent(this)
    }
}
package com.example.kotlindemo.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.databinding.ActivityTestBinding
import com.example.kotlindemo.viewmodel.state.MainViewModel

/**
 * 测试沉浸式状态栏
 */
class TestActivity : BaseActivity<MainViewModel,ActivityTestBinding>() {


    override fun layoutId(): Int = R.layout.activity_test

    override fun initView(savedInstanceState: Bundle?) {
    }
}
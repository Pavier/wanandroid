package com.example.kotlindemo.ui.activity

import android.os.Bundle
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.databinding.ActivityMainBinding
import com.example.kotlindemo.viewmodel.state.MainViewModel

class MainActivity : BaseActivity<MainViewModel,ActivityMainBinding>(){
    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun layoutId(): Int = R.layout.activity_main

}
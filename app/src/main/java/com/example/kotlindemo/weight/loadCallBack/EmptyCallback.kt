package com.example.kotlindemo.weight.loadCallBack


import com.example.kotlindemo.R
import com.kingja.loadsir.callback.Callback


class EmptyCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_empty
    }

}
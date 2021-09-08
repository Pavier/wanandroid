package com.example.kotlindemo.viewmodel.state

import com.example.kotlindemo.utils.IconUtil
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.callback.databind.StringObservableField

/**
 * @author Created by pw
 * @description:
 * @date:2021/8/27 14:07
 */
class MeViewModel :BaseViewModel() {

    var name = StringObservableField("---")

    var imageUrl = StringObservableField(IconUtil.randomImage())
}
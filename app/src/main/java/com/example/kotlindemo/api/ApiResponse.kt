package com.example.kotlindemo.api

import me.hgj.jetpackmvvm.network.BaseResponse

/**
 * @author Created by pw
 * @description:
 * @date:2021/8/10 15:47
 */
data class ApiResponse<T>(var errorCode:Int, var errorMsg:String, var data: T) : BaseResponse<T>() {
    override fun getResponseCode(): Int = errorCode

    override fun getResponseData(): T = data

    override fun getResponseMsg(): String = errorMsg

    override fun isSucces(): Boolean = errorCode == 0

}
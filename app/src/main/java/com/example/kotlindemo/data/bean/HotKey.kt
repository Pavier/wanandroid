package com.example.kotlindemo.data.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author Created by pw
 * @description: 搜索热词
 * @date:2021/9/8 9:35
 */
@Parcelize
data class HotKey(
    val id: Int,
    val link: String,
    val name: String,
    val order: Int,
    val visible: Int
) : Parcelable
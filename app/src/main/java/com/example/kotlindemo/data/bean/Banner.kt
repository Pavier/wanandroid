package com.example.kotlindemo.data.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author Created by pw
 * @description:
 * @date:2021/8/28 17:27
 */
@Parcelize
data class Banner(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
):Parcelable
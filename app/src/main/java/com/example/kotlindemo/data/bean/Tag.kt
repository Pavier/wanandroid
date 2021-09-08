package com.example.kotlindemo.data.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author Created by pw
 * @description:
 * @date:2021/9/3 15:48
 */
@Parcelize
data class Tag(
    val name: String,
    val url: String
) : Parcelable
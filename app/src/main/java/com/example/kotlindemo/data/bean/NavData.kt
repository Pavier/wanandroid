package com.example.kotlindemo.data.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author Created by pw
 * @description:
 * @date:2021/9/7 9:35
 */
@Parcelize
data class NavData(
    val articles: List<Article>,
    val cid: Int,
    val name: String
) : Parcelable

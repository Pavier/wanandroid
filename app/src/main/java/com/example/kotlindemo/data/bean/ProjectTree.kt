package com.example.kotlindemo.data.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

/**
 * @author Created by pw
 * @description:
 * @date:2021/8/30 11:05
 */
@Parcelize
data class ProjectTree(
    val children: @RawValue List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
) : Parcelable
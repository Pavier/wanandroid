package com.example.kotlindemo.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.example.kotlindemo.api.NetWorkApi
import com.example.kotlindemo.api.stateCallback.ListDataUiState
import com.example.kotlindemo.data.bean.Article
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.request

/**
 * @author Created by pw
 * @description:
 * @date:2021/9/7 15:53
 */
class RequestWendaViewModel : BaseViewModel() {

    private var pageNo : Int = 1

    val result : MutableLiveData<ListDataUiState<Article>> = MutableLiveData()

    fun getWenda(isRefresh : Boolean){
        if(isRefresh){
            pageNo = 1
        }
        request({
            NetWorkApi.service.getWenda(pageNo)
        },{
            pageNo++
            val listDataUiState = ListDataUiState(
                isSuccess = true,
                isRefresh = isRefresh,
                isEmpty = it.isEmpty(),
                hasMore = it.hasMore(),
                isFirstEmpty = isRefresh && it.isEmpty(),
                listData = it.datas
            )
            result.value = listDataUiState
        },{
            val listDataUiState = ListDataUiState(
                isSuccess = false,
                isRefresh = isRefresh,
                errMessage = it.errorMsg,
                listData = arrayListOf<Article>()
            )
            result.value = listDataUiState
        })
    }
}
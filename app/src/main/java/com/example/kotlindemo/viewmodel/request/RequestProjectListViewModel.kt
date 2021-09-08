package com.example.kotlindemo.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.example.kotlindemo.api.NetWorkApi
import com.example.kotlindemo.api.stateCallback.ListDataUiState
import com.example.kotlindemo.data.bean.Article
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.request
import me.hgj.jetpackmvvm.state.ResultState

/**
 * @author Created by pw
 * @description:
 * @date:2021/8/30 11:51
 */
class RequestProjectListViewModel : BaseViewModel() {

    private var pageNo : Int = 0

    val result : MutableLiveData<ListDataUiState<Article>> = MutableLiveData()

    fun getProjectList(cid : Int,isRefresh : Boolean){
        if(isRefresh){
            pageNo = 0
        }
        request({
            NetWorkApi.service.getProjectList(pageNo,cid)
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
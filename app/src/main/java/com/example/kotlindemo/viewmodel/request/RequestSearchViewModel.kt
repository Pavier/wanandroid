package com.example.kotlindemo.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ToastUtils
import com.example.kotlindemo.api.NetWorkApi
import com.example.kotlindemo.api.stateCallback.ListDataUiState
import com.example.kotlindemo.data.bean.Article
import com.example.kotlindemo.data.bean.HotKey
import com.example.kotlindemo.utils.CacheUtil
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.launch
import me.hgj.jetpackmvvm.ext.request
import me.hgj.jetpackmvvm.state.ResultState

/**
 * @author Created by pw
 * @description:
 * @date:2021/9/8 9:29
 */
class RequestSearchViewModel : BaseViewModel() {

    private var pageNo : Int = 0

    val hotkeyResult : MutableLiveData<ResultState<ArrayList<HotKey>>> = MutableLiveData()

    val historyResult : MutableLiveData<ArrayList<String>> = MutableLiveData()

    val queryResult : MutableLiveData<ListDataUiState<Article>> = MutableLiveData()

    fun getHotKey(){
        request({
            NetWorkApi.service.getHotKey()
        },hotkeyResult)
    }

    fun getHistory(){
        launch({
            CacheUtil.getSearchHistoryData()
        },{
            historyResult.value = it
        },{
            ToastUtils.showShort(it.message)
        })
    }

    fun getSearchArticle(key:String ,isRefresh : Boolean){
        if(isRefresh){
            pageNo = 0
        }
        request({
            NetWorkApi.service.getQueryArticle(pageNo,key)
        },{
            pageNo ++
            val dataUiState = ListDataUiState(
                isSuccess =  true,
                isRefresh =  true,
                isEmpty = it.isEmpty(),
                hasMore = it.hasMore(),
                isFirstEmpty = isRefresh && it.isEmpty(),
                listData = it.datas
            )
            queryResult.value = dataUiState
        },{
            val dataUiState = ListDataUiState(
                isRefresh = false,
                isSuccess = false,
                errMessage = it.errorMsg,
                listData = arrayListOf<Article>()
            )
            queryResult.value = dataUiState
        })
    }
}
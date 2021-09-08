package com.example.kotlindemo.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.example.kotlindemo.api.NetWorkApi
import com.example.kotlindemo.api.stateCallback.ListDataUiState
import com.example.kotlindemo.data.bean.Article
import com.example.kotlindemo.data.bean.Banner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.request
import me.hgj.jetpackmvvm.state.ResultState

/**
 * @author Created by pw
 * @description:
 * @date:2021/9/7 10:05
 */
class RequestTreeArticleViewModel : BaseViewModel() {

    private var pageNo: Int = 0

    //    var articleResult : MutableLiveData<ResultState<ArrayList<Article>>> = MutableLiveData()
    var articleResult : MutableLiveData<ListDataUiState<Article>> = MutableLiveData()

    fun getArticleToCid(cid : Int,isRefresh: Boolean){
        if (isRefresh) {
            pageNo = 0
        }
        request({
            NetWorkApi.service.getArticleToCid(pageNo,cid)
        }, {
            //请求成功
            pageNo++
            val listDataUiState =
                ListDataUiState(
                    isSuccess = true,
                    isRefresh = isRefresh,
                    isEmpty = it.isEmpty(),
                    hasMore = it.hasMore(),
                    isFirstEmpty = isRefresh && it.isEmpty(),
                    listData = it.datas
                )
            articleResult.value = listDataUiState
        }, {
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<Article>()
                )
            articleResult.value = listDataUiState
        })
    }

    fun getArticleToAuthor(author : String,isRefresh: Boolean){
        if (isRefresh) {
            pageNo = 0
        }
        request({
            NetWorkApi.service.getArticleToAuthor(pageNo,author)
        }, {
            //请求成功
            pageNo++
            val listDataUiState =
                ListDataUiState(
                    isSuccess = true,
                    isRefresh = isRefresh,
                    isEmpty = it.isEmpty(),
                    hasMore = it.hasMore(),
                    isFirstEmpty = isRefresh && it.isEmpty(),
                    listData = it.datas
                )
            articleResult.value = listDataUiState
        }, {
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<Article>()
                )
            articleResult.value = listDataUiState
        })
    }
}
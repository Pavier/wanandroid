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
 * @date:2021/8/27 14:50
 */
class RequestArticleViewModel : BaseViewModel() {

    private var pageNo: Int = 0

//    var articleResult : MutableLiveData<ResultState<ArrayList<Article>>> = MutableLiveData()
    var articleResult : MutableLiveData<ListDataUiState<Article>> = MutableLiveData()

    var bannerResult : MutableLiveData<ResultState<ArrayList<Banner>>> = MutableLiveData()


    fun getArticle(isRefresh: Boolean){
        if (isRefresh) {
            pageNo = 0
        }
        request({
//            NetWorkApi.service.getArticle(pageNo)
            withContext(Dispatchers.IO){
                val listData = async { NetWorkApi.service.getArticle(pageNo) }
                //如果App配置打开了首页请求置顶文章，且是第一页
                if (pageNo == 0) {
                    val topData = async {  NetWorkApi.service.getTopArticle() }
                    listData.await().data.datas.addAll(0, topData.await().data)
                    listData.await()
                } else {
                    listData.await()
                }
            }

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

    fun getBanner(){
        request({
            NetWorkApi.service.getBanner()
        },bannerResult,true)
    }
}
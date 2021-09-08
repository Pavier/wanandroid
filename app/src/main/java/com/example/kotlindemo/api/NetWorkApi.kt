package com.example.kotlindemo.api

import com.google.gson.GsonBuilder
import me.hgj.jetpackmvvm.network.BaseNetworkApi
import me.hgj.jetpackmvvm.network.CoroutineCallAdapterFactory
import me.hgj.jetpackmvvm.network.interceptor.logging.LogInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author Created by pw
 * @description:
 * @date:2021/8/10 15:10
 */
class NetWorkApi : BaseNetworkApi() {

    companion object{
        val instance : NetWorkApi by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            NetWorkApi()
        }

        val service : ApiService by lazy {
            instance.getApi(ApiService::class.java,ApiService.baseUrl)
        }
    }

    override fun setHttpClientBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        builder.apply {
            addInterceptor(LogInterceptor())
            connectTimeout(10,TimeUnit.SECONDS)
            readTimeout(5,TimeUnit.SECONDS)
            writeTimeout(5,TimeUnit.SECONDS)
        }
        return builder;
    }

    override fun setRetrofitBuilder(builder: Retrofit.Builder): Retrofit.Builder {

        return builder.apply {
            addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        };
    }
}
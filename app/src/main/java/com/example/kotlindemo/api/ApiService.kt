package com.example.kotlindemo.api

import com.example.kotlindemo.data.bean.*
import retrofit2.http.*

/**
 * @author Created by pw
 * @description:
 * @date:2021/8/10 15:39
 */
interface ApiService {

    companion object{
        const val baseUrl = "https://www.wanandroid.com"
    }

    /**
     * 登录
     */
    @POST("/user/login")
    @FormUrlEncoded
    suspend fun login(@Field("username") username:String,@Field("password")password:String) : ApiResponse<UserInfo>

    /**
     * 退出登录
     */
    @GET("/user/logout/json")
    suspend fun logout() : ApiResponse<Any?>

    /**
     * 首页文章
     */
    @GET("/article/list/{page}/json")
    suspend fun getArticle(@Path("page") page : Int) : ApiResponse<ApiPagerResponse<ArrayList<Article>>>

    /**
     * 置顶文章
     */
    @GET("/article/top/json")
    suspend fun getTopArticle() : ApiResponse<ArrayList<Article>>

    /**
     * 搜索热词
     */
    @GET("/hotkey/json")
    suspend fun getHotKey() : ApiResponse<ArrayList<HotKey>>

    /**
     * 搜索
     */
    @POST("/article/query/{page}/json")
    @FormUrlEncoded
    suspend fun getQueryArticle(@Path("page") page: Int, @Field("k") key : String) : ApiResponse<ApiPagerResponse<ArrayList<Article>>>

    /**
     * banner
     */
    @GET("/banner/json")
    suspend fun getBanner() : ApiResponse<ArrayList<Banner>>

    @GET("/project/tree/json")
    suspend fun getProjectTree() : ApiResponse<ArrayList<ProjectTree>>

    /**
     * 项目列表
     */
    @GET("/project/list/{page}/json")
    suspend fun getProjectList(@Path("page") page : Int,@Query("cid") cid : Int) : ApiResponse<ApiPagerResponse<ArrayList<Article>>>

    /**
     * 广场列表
     */
    @GET("/user_article/list/{page}/json")
    suspend fun getUserArticle(@Path("page") page : Int) : ApiResponse<ApiPagerResponse<ArrayList<Article>>>
    /**
     * 问答列表
     */
    @GET("/wenda/list/{page}/json ")
    suspend fun getWenda(@Path("page") page : Int) : ApiResponse<ApiPagerResponse<ArrayList<Article>>>

    /**
     * 体系数据
     */
    @GET("/tree/json")
    suspend fun getTreeData() : ApiResponse<ArrayList<TreeData>>

    /**
     * 搂分类的id 搜索
     */
    @GET("/article/list/{page}/json")
    suspend fun getArticleToCid(@Path("page") page: Int,@Query("cid") cid: Int) : ApiResponse<ApiPagerResponse<ArrayList<Article>>>
    /**
     * 按作者昵称搜索文章
     */
    @GET("/article/list/{page}/json")
    suspend fun getArticleToAuthor(@Path("page") page: Int,@Query("author") author: String) : ApiResponse<ApiPagerResponse<ArrayList<Article>>>

    /**
     * 导航数据
     */
    @GET("/navi/json")
    suspend fun getNav() : ApiResponse<ArrayList<NavData>>

    /**
     * 公众号
     */
    @GET("/wxarticle/chapters/json")
    suspend fun getWxArticleTab() : ApiResponse<ArrayList<ProjectTree>>

    /**
     * 公众号文章
     */
    @GET("/wxarticle/list/{cid}/{page}/json")
    suspend fun getWxArticle(@Path("page") page: Int,@Path("cid") cid: Int) : ApiResponse<ApiPagerResponse<ArrayList<Article>>>

}

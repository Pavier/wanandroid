package com.example.kotlindemo.ui.fragment.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.data.bean.Banner
import com.example.kotlindemo.databinding.FragmentHomeBinding
import com.example.kotlindemo.ext.*
import com.example.kotlindemo.ui.activity.TestActivity
import com.example.kotlindemo.ui.adapter.ArticleAdapter
import com.example.kotlindemo.ui.adapter.HomeBannerAdapter
import com.example.kotlindemo.ui.adapter.viewholder.HomeBannerViewHolder
import com.example.kotlindemo.viewmodel.request.RequestArticleViewModel
import com.example.kotlindemo.viewmodel.state.HomeViewModel
import com.example.kotlindemo.weight.recyclerview.DefineLoadMoreView
import com.example.kotlindemo.weight.recyclerview.SpaceItemDecoration
import com.kingja.loadsir.core.LoadService
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import com.zhpan.bannerview.BannerViewPager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.title_topbar.*
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction
import me.hgj.jetpackmvvm.ext.parseState
import me.hgj.jetpackmvvm.ext.util.dp2px

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    private val articleAdapter : ArticleAdapter by lazy { ArticleAdapter(arrayListOf()) }

    private val requestArticleViewModel : RequestArticleViewModel by viewModels()

    //recyclerview的底部加载view 因为在首页要动态改变他的颜色，所以加了他这个字段
    private lateinit var footView: DefineLoadMoreView

    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>

    override fun layoutId(): Int = R.layout.fragment_home

    override fun lazyLoadData() {
        //设置界面 加载中
        loadsir.showLoading()
        // 请求banner数据
        requestArticleViewModel.getBanner()
        //请求文章列表数据
        requestArticleViewModel.getArticle(true)
    }

    override fun initView(savedInstanceState: Bundle?) {
        //状态页配置
        loadsir = loadServiceInit(swipeRefresh) {
            //点击重试时触发的操作
            loadsir.showLoading()
            requestArticleViewModel.getBanner()
            requestArticleViewModel.getArticle(true)
        }
        // 初始化topbar
        topbar.apply {
            setTitle("首页")

            val addRightImageButton = addRightImageButton(R.mipmap.ic_search, id)
            addRightImageButton.apply {
                scaleType = ImageView.ScaleType.FIT_CENTER
                layoutParams.apply {
                    width = dp2px(35)
                }
                setPaddingRelative(0,0,dp2px(10),0)
                setOnClickListener {
                    nav().navigate(R.id.action_mainFragment_to_searchFragment)
                }
            }
        }
        //初始化recyclerView
        recyclerView.init(LinearLayoutManager(context), articleAdapter).let {
            //firstNeedTop字段 第一条数据是否需要间距，true 需要  false 不需要
            it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f), false))
            footView = it.initFooter {
                requestArticleViewModel.getArticle(false)
            }
        }

        //初始化 SwipeRefreshLayout
        swipeRefresh.init {
            //触发刷新监听时请求数据
            requestArticleViewModel.getArticle(true)
        }

        articleAdapter.setOnItemClickListener { adapter, view, position ->
            nav().navigate(R.id.action_to_webFragment,Bundle().apply {
                putParcelable("articleData",articleAdapter.data[position])
            })
//            startActivity(Intent(context, TestActivity::class.java))
        }
    }

    override fun createObserver() {
        requestArticleViewModel.apply {
            articleResult.observe(viewLifecycleOwner,{

                loadListData(it,articleAdapter, loadsir, recyclerView, swipeRefresh)
            })

            bannerResult.observe(viewLifecycleOwner,{
                parseState(it,{
                    if(recyclerView.headerCount == 0){
                        val headView = LayoutInflater.from(context).inflate(R.layout.include_banner,null).apply {
                            findViewById<BannerViewPager<Banner,HomeBannerViewHolder>>(R.id.banner_view).apply {
                                adapter = HomeBannerAdapter()
                                setLifecycleRegistry(lifecycle)
                                setOnPageClickListener {
                                    nav().navigateAction(R.id.action_to_webFragment, Bundle().apply {
                                        putParcelable("bannerData", data[it])})
                                }
                                create(it)
                            }
                        }
                        recyclerView.addHeaderView(headView)
                        recyclerView.scrollToPosition(0)
                    }
                })
            })
        }
    }
}
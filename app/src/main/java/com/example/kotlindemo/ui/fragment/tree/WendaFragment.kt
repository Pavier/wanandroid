package com.example.kotlindemo.ui.fragment.tree

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.data.bean.Article
import com.example.kotlindemo.databinding.FragmentWendaBinding
import com.example.kotlindemo.ext.*
import com.example.kotlindemo.ui.adapter.ArticleAdapter
import com.example.kotlindemo.viewmodel.request.RequestWendaViewModel
import com.example.kotlindemo.viewmodel.state.WenDaViewModel
import com.example.kotlindemo.weight.recyclerview.DefineLoadMoreView
import com.example.kotlindemo.weight.recyclerview.SpaceItemDecoration
import com.kingja.loadsir.core.LoadService
import kotlinx.android.synthetic.main.fragment_user_article.*
import me.hgj.jetpackmvvm.ext.nav

/**
 * 每日一问
 */
class WendaFragment : BaseFragment<WenDaViewModel,FragmentWendaBinding>() {
    companion object {
        fun newInstance() : WendaFragment {
            return WendaFragment()
        }
    }

    private val requestWendaViewModel : RequestWendaViewModel by viewModels()

    private val articleAdapter : ArticleAdapter by lazy { ArticleAdapter(arrayListOf()) }

    //recyclerview的底部加载view 因为在首页要动态改变他的颜色，所以加了他这个字段
    private lateinit var footView: DefineLoadMoreView

    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>

    override fun layoutId(): Int = R.layout.fragment_wenda

    override fun initView(savedInstanceState: Bundle?) {
        loadsir = loadServiceInit(swipeRefresh) {
            loadsir.showLoading()
            requestWendaViewModel.getWenda( true)
        }

        //初始化recyclerView
        recyclerView.init(LinearLayoutManager(context), articleAdapter).let {
            //firstNeedTop字段 第一条数据是否需要间距，true 需要  false 不需要
            it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f), true))
            footView = it.initFooter {
                requestWendaViewModel.getWenda(false)
            }
        }

        swipeRefresh.init {
            requestWendaViewModel.getWenda(true)
        }

        articleAdapter.setOnItemClickListener { adapter, view, position ->
            nav().navigate(R.id.action_to_webFragment,Bundle().apply {
                putParcelable("articleData",adapter.data[position] as Article)
            })
        }
    }

    override fun lazyLoadData() {
        loadsir.showLoading()
        requestWendaViewModel.getWenda(true)
    }


    override fun createObserver() {
        requestWendaViewModel.result.observe(viewLifecycleOwner,{
            loadListData(it,articleAdapter,loadsir,recyclerView,swipeRefresh)
        })
    }
}
package com.example.kotlindemo.ui.fragment.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.databinding.FragmentSearchDetailBinding
import com.example.kotlindemo.ext.*
import com.example.kotlindemo.ui.adapter.ArticleAdapter
import com.example.kotlindemo.viewmodel.request.RequestSearchViewModel
import com.example.kotlindemo.viewmodel.state.SearchViewModel
import com.example.kotlindemo.weight.recyclerview.DefineLoadMoreView
import com.example.kotlindemo.weight.recyclerview.SpaceItemDecoration
import com.kingja.loadsir.core.LoadService
import kotlinx.android.synthetic.main.fragment_search_detail.*
import kotlinx.android.synthetic.main.title_topbar.*
import me.hgj.jetpackmvvm.ext.nav

class SearchDetailFragment : BaseFragment<SearchViewModel,FragmentSearchDetailBinding>() {

    private val requestSearchViewModel : RequestSearchViewModel by viewModels()

    private val articleAdapter : ArticleAdapter by lazy { ArticleAdapter(arrayListOf()) }

    private lateinit var loadSir : LoadService<Any>

    private lateinit var footerView: DefineLoadMoreView

    override fun layoutId(): Int = R.layout.fragment_search_detail

    override fun initView(savedInstanceState: Bundle?) {
        arguments?.let {
            mViewModel.text = it.getString("text","")
        }

        loadSir = loadServiceInit(swipeRefresh){
            loadSir.showLoading()
            requestSearchViewModel.getSearchArticle(mViewModel.text,true)
        }

        topbar.run {
            setTitle(mViewModel.text)

            addLeftBackImageButton().setOnClickListener {
                nav().navigateUp()
            }
        }

        recyclerView.init(LinearLayoutManager(context),articleAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0,ConvertUtils.dp2px(8f),true))
            footerView = it.initFooter{
                requestSearchViewModel.getSearchArticle(mViewModel.text,false)
            }
        }

        articleAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                nav().navigate(R.id.action_to_webFragment,Bundle().apply {
                    putParcelable("article",articleAdapter.data[position])
                })
            }
        }

        swipeRefresh.init {
            requestSearchViewModel.getSearchArticle(mViewModel.text,true)
        }
    }

    override fun lazyLoadData() {
        loadSir.showLoading()
        requestSearchViewModel.getSearchArticle(mViewModel.text,true)
    }

    override fun createObserver() {
        requestSearchViewModel.queryResult.observe(viewLifecycleOwner,{
            loadListData(it,articleAdapter,loadSir,recyclerView,swipeRefresh)
        })
    }


}
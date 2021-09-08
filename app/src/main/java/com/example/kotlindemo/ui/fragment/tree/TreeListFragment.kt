package com.example.kotlindemo.ui.fragment.tree

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.databinding.FragmentTreeListBinding
import com.example.kotlindemo.ext.*
import com.example.kotlindemo.ui.adapter.ArticleAdapter
import com.example.kotlindemo.viewmodel.request.RequestTreeArticleViewModel
import com.example.kotlindemo.viewmodel.state.TreeListViewModel
import com.example.kotlindemo.weight.recyclerview.DefineLoadMoreView
import com.example.kotlindemo.weight.recyclerview.SpaceItemDecoration
import com.kingja.loadsir.core.LoadService
import kotlinx.android.synthetic.main.fragment_tree_list.*

class TreeListFragment : BaseFragment<TreeListViewModel,FragmentTreeListBinding>() {

    companion object{
        fun newInstance(cid : Int) : TreeListFragment {
            val fragment = TreeListFragment()
            fragment.arguments = Bundle().apply {
                putInt("cid",cid)
            }
            return fragment
        }
        fun newInstance(author : String) : TreeListFragment {
            val fragment = TreeListFragment()
            fragment.arguments = Bundle().apply {
                putString("author",author)
            }
            return fragment
        }
    }

    private val requestTreeArticleViewModel : RequestTreeArticleViewModel by viewModels()

    private val articleAdapter : ArticleAdapter by lazy { ArticleAdapter(arrayListOf()) }

    private lateinit var loadSir: LoadService<Any>

    private lateinit var footerView : DefineLoadMoreView

    override fun layoutId(): Int = R.layout.fragment_tree_list

    override fun initView(savedInstanceState: Bundle?) {
        arguments?.let {
            mViewModel.cid = it.getInt("cid")
            mViewModel.author = it.getString("author","")
        }

        loadSir = loadServiceInit(swipeRefresh){
            loadSir.showLoading()
            request(true)
        }

        //初始化recyclerView
        recyclerView.init(LinearLayoutManager(context), articleAdapter).let {
            //firstNeedTop字段 第一条数据是否需要间距，true 需要  false 不需要
            it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f), true))
            footerView = it.initFooter {
                request(false)
            }
        }

        swipeRefresh.init {
            request(true)
        }
    }

    override fun lazyLoadData() {
        loadSir.showLoading()
        request(true)
    }

    override fun createObserver() {
        requestTreeArticleViewModel.articleResult.observe(viewLifecycleOwner,{
            loadListData(it,articleAdapter,loadSir,recyclerView,swipeRefresh)
        })
    }

    private fun request(isRefresh : Boolean){
        if(mViewModel.cid == 0){
            requestTreeArticleViewModel.getArticleToAuthor(mViewModel.author,isRefresh)
        }else{
            requestTreeArticleViewModel.getArticleToCid(mViewModel.cid,isRefresh)
        }
    }
}
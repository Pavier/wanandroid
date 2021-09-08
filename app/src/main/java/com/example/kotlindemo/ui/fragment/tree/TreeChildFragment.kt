package com.example.kotlindemo.ui.fragment.tree

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.databinding.FragmentTreeChildBinding
import com.example.kotlindemo.ext.init
import com.example.kotlindemo.ext.loadServiceInit
import com.example.kotlindemo.ext.setErrorText
import com.example.kotlindemo.ext.showLoading
import com.example.kotlindemo.ui.adapter.NavAdapter
import com.example.kotlindemo.ui.adapter.TreeAdapter
import com.example.kotlindemo.viewmodel.request.RequestTreeViewModel
import com.example.kotlindemo.viewmodel.state.TreeViewModel
import com.example.kotlindemo.weight.loadCallBack.ErrorCallback
import com.example.kotlindemo.weight.recyclerview.SpaceItemDecoration
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import kotlinx.android.synthetic.main.fragment_tree_child.*
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.parseState

class TreeChildFragment : BaseFragment<TreeViewModel,FragmentTreeChildBinding>() {

    private val TAB_TREE : Int = 1 // tab栏  体系选中
    private val TAB_NAV : Int = 2 // tab栏   导航选中

    private val requestTreeViewModel : RequestTreeViewModel by viewModels()

    private val treeAdapter : TreeAdapter by lazy { TreeAdapter(arrayListOf()) }

    private val navAdapter : NavAdapter by lazy { NavAdapter(arrayListOf()) }

    private lateinit var loadSir: LoadService<Any>

    override fun layoutId(): Int = R.layout.fragment_tree_child

    override fun initView(savedInstanceState: Bundle?) {

        arguments?.let {
            mViewModel.tab = it.getInt("tab")
        }

        loadSir = loadServiceInit(swipeRefresh){
            loadSir.showLoading()
            request()
        }

        if(mViewModel.tab == TAB_TREE) {
            recyclerView.init(LinearLayoutManager(context), treeAdapter).let {
                it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f), true))
            }
        }else if(mViewModel.tab == TAB_NAV){
            recyclerView.init(LinearLayoutManager(context), navAdapter).let {
                it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f), true))
            }
        }

        swipeRefresh.init {
            request()
        }

        treeAdapter.setItemClick { data, view, position ->
            nav().navigate(R.id.action_mainFragment_to_treeArticleFragment,Bundle().apply {
                putParcelable("treeData",data)
                putInt("position",position)
            })
        }

        navAdapter.setItemClick { data, view, position ->
            nav().navigate(R.id.action_to_webFragment,Bundle().apply {
                putParcelable("articleData",data.articles[position])
            })
        }
    }

    private fun request() {
        if (mViewModel.tab == TAB_TREE)
            requestTreeViewModel.getTreeData()
        else if (mViewModel.tab == TAB_NAV)
            requestTreeViewModel.getNavData()
    }

    override fun lazyLoadData() {
        loadSir.showLoading()
        request()
    }

    override fun createObserver() {
        requestTreeViewModel.run {
            treeResult.observe(viewLifecycleOwner,{
                parseState(it,{
                    swipeRefresh.isRefreshing = false

                    treeAdapter.setList(it)

                    loadSir.showSuccess()
                },{
                    loadSir.showCallback(ErrorCallback::class.java)
                    loadSir.setErrorText(it.errorMsg)
                })
            })
            navResult.observe(viewLifecycleOwner,{
                parseState(it,{
                    swipeRefresh.isRefreshing = false

                    navAdapter.setList(it)

                    loadSir.showSuccess()
                },{
                    loadSir.showCallback(ErrorCallback::class.java)
                    loadSir.setErrorText(it.errorMsg)
                })
            })
        }
    }

}
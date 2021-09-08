package com.example.kotlindemo.ui.fragment.project

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.data.bean.Article
import com.example.kotlindemo.databinding.FragmentProjectListBinding
import com.example.kotlindemo.ext.*
import com.example.kotlindemo.ui.adapter.ProjectAdapter
import com.example.kotlindemo.viewmodel.request.RequestProjectListViewModel
import com.example.kotlindemo.viewmodel.state.ProjectListViewModel
import com.example.kotlindemo.weight.recyclerview.DefineLoadMoreView
import com.example.kotlindemo.weight.recyclerview.SpaceItemDecoration
import com.kingja.loadsir.core.LoadService
import kotlinx.android.synthetic.main.fragment_project_list.recyclerView
import kotlinx.android.synthetic.main.fragment_project_list.swipeRefresh
import me.hgj.jetpackmvvm.ext.nav

class ProjectListFragment : BaseFragment<ProjectListViewModel,FragmentProjectListBinding>() {

    companion object{
        fun newInstance(cid : Int) : ProjectListFragment{
            val fragment = ProjectListFragment()
            fragment.arguments = Bundle().apply {
                putInt("cid",cid)
            }
            return fragment
        }
    }

    private val requestProjectListViewModel : RequestProjectListViewModel by viewModels()

    private val projectAdapter : ProjectAdapter by lazy { ProjectAdapter(arrayListOf()) }

    //recyclerview的底部加载view 因为在首页要动态改变他的颜色，所以加了他这个字段
    private lateinit var footView: DefineLoadMoreView

    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>

    override fun layoutId(): Int = R.layout.fragment_project_list

    override fun initView(savedInstanceState: Bundle?) {
        arguments?.let {
            mViewModel.cid = it.getInt("cid")
        }
        loadsir = loadServiceInit(swipeRefresh) {
            loadsir.showLoading()
            requestProjectListViewModel.getProjectList(mViewModel.cid, true)
        }

        //初始化recyclerView
        recyclerView.init(LinearLayoutManager(context), projectAdapter).let {
            //firstNeedTop字段 第一条数据是否需要间距，true 需要  false 不需要
            it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f), true))
            footView = it.initFooter {
                requestProjectListViewModel.getProjectList(mViewModel.cid,false)
            }
        }

        swipeRefresh.init {
            requestProjectListViewModel.getProjectList(mViewModel.cid,true)
        }

        projectAdapter.setOnItemClickListener { adapter, view, position ->
            nav().navigate(R.id.action_to_webFragment,Bundle().apply {
                putParcelable("articleData",adapter.data[position] as Article)
            })
        }
    }

    override fun lazyLoadData() {
        loadsir.showLoading()
        requestProjectListViewModel.getProjectList(mViewModel.cid,true)
    }


    override fun createObserver() {
        requestProjectListViewModel.result.observe(viewLifecycleOwner,{
            loadListData(it,projectAdapter,loadsir,recyclerView,swipeRefresh)
        })
    }
}
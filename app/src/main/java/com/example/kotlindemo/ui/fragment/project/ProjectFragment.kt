package com.example.kotlindemo.ui.fragment.project

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.blankj.utilcode.util.ToastUtils
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.databinding.FragmentProjectBinding
import com.example.kotlindemo.ext.*
import com.example.kotlindemo.viewmodel.request.RequestProjectTreeViewModel
import com.example.kotlindemo.viewmodel.state.ProjectViewModel
import com.example.kotlindemo.weight.loadCallBack.ErrorCallback
import com.kingja.loadsir.core.LoadService
import kotlinx.android.synthetic.main.fragment_project.*
import kotlinx.android.synthetic.main.title_topbar.*
import me.hgj.jetpackmvvm.ext.parseState

class ProjectFragment : BaseFragment<ProjectViewModel, FragmentProjectBinding>() {

    private val requestProjectTreeViewModel : RequestProjectTreeViewModel by viewModels()

    // tab 集合
    private var stringList : ArrayList<String> = arrayListOf()
    // fragment 集合
    private var framgents : ArrayList<Fragment> = arrayListOf()

    private lateinit var loadSir: LoadService<Any>

    override fun layoutId(): Int = R.layout.fragment_project

    override fun lazyLoadData() {
        loadSir.showLoading()
        requestProjectTreeViewModel.getProjectTree()
    }

    override fun initView(savedInstanceState: Bundle?) {

        loadSir = loadServiceInit(project_viewpager) {
            loadSir.showLoading()
            requestProjectTreeViewModel.getProjectTree()
        }

        topbar.apply {
            setTitle("项目")
        }

        tabView.init(project_viewpager,stringList,action = {index ->
            ToastUtils.showShort(tabView.getTab(index).text)
        })

        project_viewpager.init(this@ProjectFragment,framgents,offscreenPageLimit = framgents.size)

    }

    override fun createObserver() {
        requestProjectTreeViewModel.projectTreeResult.observe(viewLifecycleOwner,{
            parseState(it,{
                stringList.clear()
                framgents.clear()
                stringList.addAll(it.map { it.name })
                it.forEach {tree ->
                    framgents.add(ProjectListFragment.newInstance(tree.id))
                }
                tabView.resetData(stringList)
                tabView.notifyDataChanged()
                project_viewpager.adapter?.notifyDataSetChanged()
                project_viewpager.offscreenPageLimit = framgents.size
                loadSir.showSuccess()
            },{
                loadSir.showCallback(ErrorCallback::class.java)
                loadSir.setErrorText(it.errorMsg)
            })
        })
    }
}
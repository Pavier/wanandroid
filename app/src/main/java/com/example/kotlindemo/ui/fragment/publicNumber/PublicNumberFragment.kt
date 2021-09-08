package com.example.kotlindemo.ui.fragment.publicNumber

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.blankj.utilcode.util.ToastUtils
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.databinding.FragmentPublicNumberBinding
import com.example.kotlindemo.ext.*
import com.example.kotlindemo.ui.fragment.project.ProjectListFragment
import com.example.kotlindemo.viewmodel.request.RequestPublicNumberViewModel
import com.example.kotlindemo.viewmodel.state.PublicNumberViewModel
import com.example.kotlindemo.weight.loadCallBack.ErrorCallback
import com.kingja.loadsir.core.LoadService
import kotlinx.android.synthetic.main.fragment_project.*
import kotlinx.android.synthetic.main.fragment_public_number.*
import kotlinx.android.synthetic.main.fragment_public_number.tabView
import kotlinx.android.synthetic.main.title_topbar.*
import me.hgj.jetpackmvvm.ext.parseState

class PublicNumberFragment : BaseFragment<PublicNumberViewModel, FragmentPublicNumberBinding>() {

    private val requestPublicNumberViewModel : RequestPublicNumberViewModel by viewModels()

    // tab 集合
    private var stringList : ArrayList<String> = arrayListOf()
    // fragment 集合
    private var framgents : ArrayList<Fragment> = arrayListOf()

    private lateinit var loadSir: LoadService<Any>


    override fun layoutId(): Int = R.layout.fragment_public_number

    override fun lazyLoadData() {
        loadSir.showLoading()
        requestPublicNumberViewModel.getWxArticleTab()
    }

    override fun initView(savedInstanceState: Bundle?) {

        loadSir = loadServiceInit(public_number_viewpager) {
            loadSir.showLoading()
            requestPublicNumberViewModel.getWxArticleTab()
        }

        topbar.apply {
            setTitle("公众号")
        }

        tabView.init(public_number_viewpager,stringList,action = {index ->
            ToastUtils.showShort(tabView.getTab(index).text)
        })


        public_number_viewpager.init(this@PublicNumberFragment,framgents,offscreenPageLimit = framgents.size)
    }

    override fun createObserver() {
        requestPublicNumberViewModel.publicNumberTabResult.observe(viewLifecycleOwner,{
            parseState(it,{
                stringList.clear()
                framgents.clear()
                stringList.addAll(it.map { it.name })
                it.forEach {tree ->
                    framgents.add(PNArticleFragment.newInstance(tree.id))
                }
                tabView.resetData(stringList)
                tabView.notifyDataChanged()
                public_number_viewpager.adapter?.notifyDataSetChanged()
                public_number_viewpager.offscreenPageLimit = framgents.size
                loadSir.showSuccess()
            },{
                loadSir.showCallback(ErrorCallback::class.java)
                loadSir.setErrorText(it.errorMsg)
            })
        })
    }

}
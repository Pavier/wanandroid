package com.example.kotlindemo.ui.fragment.me

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.example.kotlindemo.R
import com.example.kotlindemo.app.appViewModel
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.databinding.FragmentMeBinding
import com.example.kotlindemo.utils.CacheUtil
import com.example.kotlindemo.utils.IconUtil
import com.example.kotlindemo.viewmodel.request.RequestMeViewModel
import com.example.kotlindemo.viewmodel.state.MeViewModel
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import kotlinx.android.synthetic.main.fragment_me.*
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.parseState
import me.hgj.jetpackmvvm.ext.util.notNull

class MeFragment : BaseFragment<MeViewModel, FragmentMeBinding>() {

    private val requestMeViewModel : RequestMeViewModel by viewModels()

    override fun layoutId(): Int = R.layout.fragment_me

    override fun lazyLoadData() {

    }

    override fun onResume() {
        super.onResume()
        QMUIStatusBarHelper.setStatusBarLightMode(activity)
    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        login.setOnClickListener {
            nav().navigate(R.id.action_to_loginFragment)
        }
        head_icon.setOnClickListener {
            mViewModel.imageUrl.set(IconUtil.randomImage())
        }
        logout.setOnClickListener {
            requestMeViewModel.logout()
        }
        appViewModel.userInfo.value?.let {
            mViewModel.name.set(if (it.nickname.isEmpty()) "id：${it.id}   昵称：${it.username}" else "id：${it.id}   昵称：${it.nickname}")
            logout.visibility = View.VISIBLE
            login.visibility = View.GONE
        }
    }

    override fun createObserver() {
        requestMeViewModel.result.observe(viewLifecycleOwner){
            parseState(it,{
                CacheUtil.setUser(null)
                appViewModel.userInfo.value = null
            },{
                ToastUtils.showShort(it.errorMsg)
            })
        }

        appViewModel.run {
            userInfo.observeInFragment(this@MeFragment, Observer {
                it.notNull({
                    logout.visibility = View.VISIBLE
                    login.visibility = View.GONE
                    mViewModel.name.set(if (it.nickname.isEmpty()) "id：${it.id}   昵称：${it.username}" else "id：${it.id}   昵称：${it.nickname}")
                },{
                    logout.visibility = View.GONE
                    login.visibility = View.VISIBLE
                    mViewModel.name.set("---")
                })
            })
        }
    }
}
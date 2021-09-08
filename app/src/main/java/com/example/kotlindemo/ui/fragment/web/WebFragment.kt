package com.example.kotlindemo.ui.fragment.web

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.data.bean.Article
import com.example.kotlindemo.data.bean.Banner
import com.example.kotlindemo.databinding.FragmentWebBinding
import com.example.kotlindemo.viewmodel.state.WebViewModel
import com.just.agentweb.AgentWeb
import kotlinx.android.synthetic.main.fragment_web.*
import kotlinx.android.synthetic.main.title_topbar.*
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.util.toHtml

class WebFragment : BaseFragment<WebViewModel,FragmentWebBinding>() {

    private var mAgentWeb : AgentWeb? = null
    private var mPreWeb: AgentWeb.PreAgentWeb? = null

    override fun layoutId(): Int = R.layout.fragment_web

    override fun initView(savedInstanceState: Bundle?) {

        arguments?.run {
            getParcelable<Article>("articleData")?.let {
                mViewModel.url = it.link
                mViewModel.title = it.title
            }
            getParcelable<Banner>("bannerData")?.let {
                mViewModel.url = it.url
                mViewModel.title = it.title
            }
        }

        topbar.apply {
            setTitle(mViewModel.title.toHtml().toString())
            addLeftBackImageButton().setOnClickListener {
                mAgentWeb?.let {
                    if(it.webCreator.webView.canGoBack()){
                        it.webCreator.webView.goBack()
                    }else{
                        nav().navigateUp()
                    }
                }
            }
        }

        mPreWeb = AgentWeb.with(this)
                .setAgentWebParent(webContent,LinearLayout.LayoutParams(-1,-1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
    }

    override fun lazyLoadData() {
        mAgentWeb = mPreWeb?.go(mViewModel.url)
        requireActivity().onBackPressedDispatcher.addCallback(this,
        object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                mAgentWeb?.let {
                    if(it.webCreator.webView.canGoBack()){
                        it.webCreator.webView.goBack()
                    }else{
                        nav().navigateUp()
                    }
                }
            }

        })
    }

    override fun onResume() {
        mAgentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onPause() {
        mAgentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onDestroyView() {
        mAgentWeb?.webLifeCycle?.onDestroy()
        super.onDestroyView()
    }
}
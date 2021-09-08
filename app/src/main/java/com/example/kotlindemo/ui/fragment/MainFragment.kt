package com.example.kotlindemo.ui.fragment

import android.graphics.Color
import android.os.Bundle
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.databinding.FragmentMainBinding
import com.example.kotlindemo.ext.init
import com.example.kotlindemo.ext.initMain
import com.example.kotlindemo.ext.interceptLongClick
import com.example.kotlindemo.viewmodel.state.MainViewModel
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.title_topbar.*

/**
 * @author Created by pw
 * @description:
 * @date:2021/8/10 10:46
 */
class MainFragment : BaseFragment<MainViewModel,FragmentMainBinding>(){
    override fun layoutId(): Int = R.layout.fragment_main

    override fun initView(savedInstanceState: Bundle?) {
//        mDatabind.login.setOnClickListener { _ ->
//            parentFragmentManager.commit {
//                addToBackStack(null)
//                replace(R.id.content,LoginFragment())
//            }
//        }

        mainViewpager.initMain(this)

        bottom_navigation.init {
            QMUIStatusBarHelper.setStatusBarDarkMode(activity)
            when (it) {
                R.id.menu_main -> {
                    mainLayout.setBackgroundColor(Color.parseColor("#0066ff"))
                    mainViewpager.setCurrentItem(0, false)
                }
                R.id.menu_project -> {
                    mainLayout.setBackgroundColor(Color.parseColor("#0066ff"))
                    mainViewpager.setCurrentItem(1, false)
                }
                R.id.menu_system -> {
                    mainLayout.setBackgroundColor(Color.parseColor("#0066ff"))
                    mainViewpager.setCurrentItem(2, false)
                }
                R.id.menu_public -> {
                    mainLayout.setBackgroundColor(Color.parseColor("#0066ff"))
                    mainViewpager.setCurrentItem(3, false)
                }
                R.id.menu_me -> {
                    mainLayout.setBackgroundColor(Color.WHITE)
                    QMUIStatusBarHelper.setStatusBarLightMode(activity)
                    mainViewpager.setCurrentItem(4, false)
                }
            }
        }
        bottom_navigation.interceptLongClick(R.id.menu_main,R.id.menu_project,R.id.menu_system,R.id.menu_public,R.id.menu_me)

    }

    override fun lazyLoadData() {
    }

    override fun createObserver() {
    }
}
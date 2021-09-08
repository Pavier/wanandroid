package com.example.kotlindemo.ui.fragment.tree

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.databinding.FragmentTreeArrBinding
import com.example.kotlindemo.ext.init
import com.example.kotlindemo.ext.resetData
import com.example.kotlindemo.viewmodel.state.TreeViewModel
import com.qmuiteam.qmui.widget.tab.QMUIBasicTabSegment.MODE_FIXED
import kotlinx.android.synthetic.main.fragment_tree_arr.*
import kotlinx.android.synthetic.main.title_topbar.*

class TreeArrFragment : BaseFragment<TreeViewModel, FragmentTreeArrBinding>() {

    private val data = arrayListOf("广场","每日一问","体系","导航")

    private val fragments: ArrayList<Fragment> by lazy { arrayListOf() }

    init {
        fragments.add(UserArticleFragment.newInstance())
        fragments.add(WendaFragment.newInstance())
        for (i in 1..2)
            fragments.add(TreeChildFragment().apply {
                arguments = Bundle().apply {
                    putInt("tab",i)
                }
            })

    }

    override fun layoutId(): Int = R.layout.fragment_tree_arr

    override fun lazyLoadData() {
    }

    override fun initView(savedInstanceState: Bundle?) {

        topbar.setTitle("广场")

        tabView.init(tree_viewpager,data, mode = MODE_FIXED)

//        tabView.resetData(data,Color.LTGRAY,Color.WHITE)

        tree_viewpager.init(this,fragments,offscreenPageLimit = fragments.size)
    }
}
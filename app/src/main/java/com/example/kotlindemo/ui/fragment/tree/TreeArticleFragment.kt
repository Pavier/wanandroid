package com.example.kotlindemo.ui.fragment.tree

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.data.bean.Article
import com.example.kotlindemo.data.bean.NavData
import com.example.kotlindemo.data.bean.TreeData
import com.example.kotlindemo.databinding.FragmentTreeArticleBinding
import com.example.kotlindemo.ext.init
import com.example.kotlindemo.ext.loadServiceInit
import com.example.kotlindemo.viewmodel.request.RequestTreeArticleViewModel
import com.example.kotlindemo.viewmodel.state.TreeArticleViewModel
import com.kingja.loadsir.core.LoadService
import kotlinx.android.synthetic.main.fragment_tree_article.*
import kotlinx.android.synthetic.main.title_topbar.*
import me.hgj.jetpackmvvm.ext.nav

class TreeArticleFragment : BaseFragment<TreeArticleViewModel,FragmentTreeArticleBinding>() {

    override fun layoutId(): Int = R.layout.fragment_tree_article

    override fun initView(savedInstanceState: Bundle?) {

        arguments?.run {
            getParcelable<TreeData>("treeData")?.let {
                mViewModel.treeData = it
            }

            mViewModel.position = getInt("position")
       }

        topbar.run {
            setTitle(mViewModel.treeData.name)
            addLeftBackImageButton().setOnClickListener {
                nav().navigateUp()
            }
        }


        val tabStrings = mViewModel.treeData.children.map { it.name }
        tabView.init(tree_article_viewpager,tabStrings)

        val fragments : ArrayList<Fragment> = arrayListOf()
        mViewModel.treeData.children.forEach {
            fragments.add(TreeListFragment.newInstance(it.id))
        }

        tree_article_viewpager.init(this,fragments,offscreenPageLimit = fragments.size)


        tabView.selectTab(mViewModel.position)
    }



}
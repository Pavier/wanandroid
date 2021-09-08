package com.example.kotlindemo.ui.fragment.search

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.data.bean.Children
import com.example.kotlindemo.databinding.FragmentSearchBinding
import com.example.kotlindemo.ext.init
import com.example.kotlindemo.ui.adapter.HistoryAdapter
import com.example.kotlindemo.ui.adapter.HotKeyAdapter
import com.example.kotlindemo.ui.adapter.TreeDetailAdapter
import com.example.kotlindemo.utils.CacheUtil
import com.example.kotlindemo.viewmodel.request.RequestSearchViewModel
import com.example.kotlindemo.viewmodel.state.SearchViewModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.qmuiteam.qmui.util.QMUIKeyboardHelper
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction
import kotlinx.android.synthetic.main.fragment_search.*
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.parseState
import me.hgj.jetpackmvvm.ext.util.toJson

class SearchFragment : BaseFragment<SearchViewModel,FragmentSearchBinding>() {

    private val requestSearchViewModel : RequestSearchViewModel by viewModels()

    private val hotKeyAdapter : HotKeyAdapter by lazy { HotKeyAdapter(ArrayList()) }

    private val historyAdapter : HistoryAdapter by lazy { HistoryAdapter(ArrayList()) }

    override fun layoutId(): Int = R.layout.fragment_search

    override fun initView(savedInstanceState: Bundle?) {
        back.setOnClickListener {
            if(QMUIKeyboardHelper.isKeyboardVisible(activity)){
                QMUIKeyboardHelper.hideKeyboard(it)
            }else {
                nav().navigateUp()
            }
        }

        search_text.run {
            setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    val text = this.text.toString()
                    if(text.isNotEmpty()){
                        this.text.clear()
                        updateHistory(text)
                        QMUIKeyboardHelper.hideKeyboard(view)
                        nav().navigate(R.id.action_searchFragment_to_searchDetailFragment,Bundle().apply {
                            putString("text",text)
                        })
                    }

                }
                false
            }
        }
        search.run {
            setOnClickListener {
                val text = search_text.text.toString()
                if(text.isNotEmpty()){
                    search_text.text.clear()
                    updateHistory(text)
                    QMUIKeyboardHelper.hideKeyboard(view)
                    nav().navigate(R.id.action_searchFragment_to_searchDetailFragment,Bundle().apply {
                        putString("text",text)
                    })
                }
            }
        }

        hotkey_recyclerView.run {
            layoutManager = FlexboxLayoutManager(context).apply {
                flexDirection = FlexDirection.ROW
                flexWrap = FlexWrap.WRAP
                justifyContent = JustifyContent.FLEX_START
            }
            setHasFixedSize(true)
            setItemViewCacheSize(200)
            isNestedScrollingEnabled = false
            adapter = hotKeyAdapter
        }
        hotKeyAdapter.apply {
            setOnItemClickListener { adapter, view, position ->
                updateHistory(hotKeyAdapter.data[position].name)
                QMUIKeyboardHelper.hideKeyboard(view)
                nav().navigate(R.id.action_searchFragment_to_searchDetailFragment,Bundle().apply {
                    putString("text",hotKeyAdapter.data[position].name)
                })
            }
        }

        history_recyclerView.init(LinearLayoutManager(context),historyAdapter)

        historyAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                updateHistory(historyAdapter.data[position])
                QMUIKeyboardHelper.hideKeyboard(view)
                nav().navigate(R.id.action_searchFragment_to_searchDetailFragment,Bundle().apply {
                    putString("text",historyAdapter.data[position])
                })
            }
            addChildClickViewIds(R.id.delete)
            setOnItemChildClickListener { adapter, view, position ->
                when(view.id){
                    R.id.delete -> {
                        requestSearchViewModel.historyResult.value?.let {
                            it.removeAt(position)
                            requestSearchViewModel.historyResult.value = it
                        }
                    }
                }
            }
        }

        clear_history.setOnClickListener {
            QMUIDialog.MessageDialogBuilder(context).apply {
                setTitle("提示")
                setMessage("确定清空吗？")
                addAction(0,"取消",QMUIDialogAction.ACTION_PROP_NEGATIVE) { dialog, index ->
                    dialog.dismiss()
                }
                addAction(0,"确定",QMUIDialogAction.ACTION_PROP_POSITIVE) { dialog, index ->
                    requestSearchViewModel.historyResult.value?.let {
                        it.clear()
                        requestSearchViewModel.historyResult.value = it
                    }
                    dialog.dismiss()
                }
                create().show()
            }
        }


    }

    override fun onResume() {
        super.onResume()
        // 进入页面弹出软键盘
        QMUIKeyboardHelper.showKeyboard(search_text,false)
    }

    override fun lazyLoadData() {
        requestSearchViewModel.getHotKey()
        requestSearchViewModel.getHistory()
    }

    override fun createObserver() {
        requestSearchViewModel.apply {
            hotkeyResult.observe(viewLifecycleOwner){
                parseState(it,{
                    hotKeyAdapter.data = it
                    hotKeyAdapter.notifyDataSetChanged()
                },{
                    ToastUtils.showShort(it.errorMsg)
                })
            }

            historyResult.observe(viewLifecycleOwner){
                historyAdapter.data = it
                historyAdapter.notifyDataSetChanged()
                CacheUtil.setSearchHistoryData(it.toJson())
            }
        }
    }

    private fun updateHistory(str : String){
        requestSearchViewModel.historyResult.value?.let {
            if(it.contains(str)){
                it.remove(str)
            }else if(it.size > 10){
                it.removeAt(it.size-1)
            }
            it.add(0,str)
            requestSearchViewModel.historyResult.value = it
        }
    }

}
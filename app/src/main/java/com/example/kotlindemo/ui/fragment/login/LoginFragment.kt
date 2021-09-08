package com.example.kotlindemo.ui.fragment.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.example.kotlindemo.R
import com.example.kotlindemo.app.appViewModel
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.databinding.FragmentLoginBinding
import com.example.kotlindemo.utils.CacheUtil
import com.example.kotlindemo.viewmodel.request.RequestLoginViewModel
import com.example.kotlindemo.viewmodel.state.LoginViewModel
import com.qmuiteam.qmui.util.QMUIKeyboardHelper
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import kotlinx.android.synthetic.main.title_topbar.*
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.parseState

/**
 * @author Created by pw
 * @description:
 * @date:2021/8/10 11:14
 */
class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {

    private val requestLoginViewModel : RequestLoginViewModel by viewModels()

    override fun layoutId(): Int = R.layout.fragment_login

    override fun initView(savedInstanceState: Bundle?) {
        QMUIStatusBarHelper.setStatusBarDarkMode(activity)
        mDatabind.viewmodel = mViewModel
        mDatabind.click = ProxyClick()
        topbar.run {
            setTitle("登录")
            addLeftBackImageButton().setOnClickListener {
                nav().navigateUp()
            }
        }
    }

    override fun lazyLoadData() {
    }

    override fun createObserver() {
        requestLoginViewModel.loginResult.observe(viewLifecycleOwner,
            Observer {resultState ->
                parseState(resultState,{
                    mViewModel.userinfo.set(it.toString())
                    CacheUtil.setUser(it)
                    appViewModel.userInfo.value = it
                    nav().navigateUp()
                },{
                    mViewModel.userinfo.set(it.errorMsg)
                    ToastUtils.showShort(it.errorMsg)
                })
        })
    }


    inner class ProxyClick{

        fun generatePassword(view: View,length : Int){
//            var length = 16; // 长度
            var chk1 = true // 小写字母
            var chku = false // 大写字母
            var chkn = true // 数字
            var chksc = false // 特殊字符

            var pwdstring = "";
            var string = "abcdefghijklmnopqrstuvwxyz"
            var strUpper="ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            var numeric = "0123456789"
            var punctuation = "!@#$%^&*()_+~`|}{[]\\:;?><,./-="
            if(chk1) pwdstring += string
            if(chku) pwdstring += strUpper
            if(chkn) pwdstring += numeric
            if(chksc) pwdstring += punctuation

            var password = ""
            while( password.length<length ) {
                password += pwdstring[(0..(pwdstring.length-1)).random()]
            }
            if(password.isNotBlank()) {
                println("生成的密码是：${password}")
            }
            when(view.id){
                R.id.generate_username -> mViewModel.username.set(password)

                R.id.generate_password -> mViewModel.passwrod.set(password)
            }
        }

        fun login(){
            when{
                mViewModel.username.get().isEmpty() -> Toast.makeText(this@LoginFragment.context,"请填写账号",Toast.LENGTH_SHORT).show()
                mViewModel.passwrod.get().isEmpty() -> Toast.makeText(this@LoginFragment.context,"请填写密码",Toast.LENGTH_SHORT).show()
                else -> {
                    QMUIKeyboardHelper.hideKeyboard(view)
                    requestLoginViewModel.login(
                        mViewModel.username.get(),
                        mViewModel.passwrod.get()
                    )
                }
            }

        }
    }


}
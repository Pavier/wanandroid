package com.example.kotlindemo.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.databinding.ActivitySplashBinding
import kotlinx.android.synthetic.main.activity_splash.*
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

class SplashActivity : BaseActivity<BaseViewModel,ActivitySplashBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        startAnim()
    }

    override fun layoutId(): Int = R.layout.activity_splash


    private fun startAnim(){
        val anim = AlphaAnimation(0.1f,1.0f)
        anim.duration = 1000
        img.startAnimation(anim)
        anim.setAnimationListener(object :Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                startActivity(Intent(this@SplashActivity,MainActivity::class.java))
                finish()
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }

        })
    }
}
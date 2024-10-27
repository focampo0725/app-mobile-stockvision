package com.upc.stockvision.presentation.ui.splashscreen

import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.upc.stockvision.R
import com.upc.stockvision.databinding.ActivitySplashScreenBinding
import com.upc.stockvision.presentation.BaseActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class SplashScreenActivity : BaseActivity<SplashScreenViewModel, SplashScreenState>() {
    private val viewModel: SplashScreenViewModel by viewModels()
    private lateinit var binding: ActivitySplashScreenBinding

    @Inject
    @ApplicationContext
    lateinit var context : Context

    override fun processRenderState(renderState: SplashScreenState, context: Context) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadSlideInAnimation()
        loadScaleUpAnimation()
    }

    fun loadScaleUpAnimation() {

        Handler(Looper.getMainLooper()).postDelayed({
            binding.tvStock.setTextColor(ContextCompat.getColor(this, R.color.text_animation))
            binding.imgSmartphoneLogo.setColorFilter(ContextCompat.getColor(this, R.color.text_animation), PorterDuff.Mode.SRC_IN)
            val animation = AnimationUtils.loadAnimation(this, R.anim.scale_up)
            binding.animatedCircle.visibility = View.VISIBLE
            binding.animatedCircle.startAnimation(animation)
        }, 1600) // 2000 milisegundos = 2 segundos
    }

    fun loadSlideInAnimation() {
        Thread.sleep(1500)
        val slideInLeft = AnimationUtils.loadAnimation(this, R.anim.slide_in_left)
        val slideInRight = AnimationUtils.loadAnimation(this, R.anim.slide_in_right)
        val scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        binding.imgSmartphoneLogo.startAnimation(scaleUp)
        binding.tvStock.startAnimation(slideInLeft)
        binding.tvVision.startAnimation(slideInRight)
    }


}
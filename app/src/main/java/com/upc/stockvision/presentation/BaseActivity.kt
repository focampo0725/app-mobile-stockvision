package com.upc.stockvision.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.upc.stockvision.infrastructure.extensions.LCEState
import com.upc.stockvision.infrastructure.extensions.LoadingTYPE
import com.upc.stockvision.infrastructure.extensions.logi

abstract class BaseActivity <T:IViewModel<K>,K>: AppCompatActivity() {


    fun updateUI(state : LCEState<K>){
        when (state) {
            is LCEState.Loading -> onLoading(isStarted = (state.state == LoadingTYPE.START))
            is LCEState.Content -> processRenderState(state.content, this@BaseActivity)
            is LCEState.Error -> logi("error >> ${state.error}")
            else -> {}
        }
    }

    open fun onLoading(isStarted : Boolean){}
    abstract fun processRenderState(renderState: K, context: Context)
    fun setupViewModel(viewModel : T){
        viewModel.renderState.observe(this, ::updateUI)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        inmerviseScreen(window)
    }


    fun inmerviseScreen(window: Window) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        )
        window.addFlags(
            WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM or
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
    }


}
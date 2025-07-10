package com.upc.stockvision.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.upc.stockvision.infrastructure.extensions.LCEState
import com.upc.stockvision.infrastructure.extensions.LoadingTYPE
import com.upc.stockvision.infrastructure.extensions.logi
interface OnToGoNewActivity {
    fun onNextActivity(cls : Class<*>, bundle : Bundle?, isFinish : Boolean = false)
}
abstract class BaseActivity <T:IViewModel<K>,K>: AppCompatActivity(), OnToGoNewActivity {

    var isClosureDone = false
    val closureOnStartDelay = Runnable {
        // Do what ever you want
//        SecurityUtils.onStart()
//        SecurityUtils.activity = this
        isClosureDone = true
    }
    private val handler = Handler(Looper.getMainLooper())
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

    override fun onNextActivity(cls: Class<*>, bundle : Bundle?, isFinish : Boolean) {
        val intent = Intent(this, cls)
        bundle?.let {
            intent.putExtras(bundle)
        }
        startActivity(intent)
//        overridePendingTransition(R.anim.slide_in_right, R.anim.no_animation);
        if (isFinish)
        {
            handler.removeCallbacks(closureOnStartDelay)
            finish()
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
//        inmerviseScreen(window)
    }


    fun immersiveScreen(window: Window) {
        // Oculta la barra de navegación y status bar
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)

        // Si ya lo aplicaste antes con FLAG_NOT_FOCUSABLE, asegúrate de quitarlo:
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
    }


}
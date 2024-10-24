package com.upc.stockvision.presentation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.upc.stockvision.infrastructure.extensions.LCEState
import com.upc.stockvision.infrastructure.extensions.LoadingTYPE
import com.upc.stockvision.infrastructure.extensions.logi

abstract class BaseFragment<T : IViewModel<K>, K> : Fragment() {

    fun updateUI(state : LCEState<K>){
        when (state) {
            is LCEState.Loading -> onLoading(isStarted = (state.state == LoadingTYPE.START))
            is LCEState.Content -> processRenderState(state.content, requireActivity())
            is LCEState.Error -> requireContext().logi("error >> ${state.error}")

        }
    }

    open fun onLoading(isStarted : Boolean){}
    abstract fun processRenderState(renderState: K, context: Context)

    fun setupViewModel(viewModel : T){

        viewModel.renderState.observe(this, ::updateUI)
        //posibilidad de usar : viewLifecycleOwner
//        viewModel.renderState.observe(viewLifecycleOwner, ::updateUI)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
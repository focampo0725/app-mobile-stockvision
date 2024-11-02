package com.upc.stockvision.presentation.ui.incoming_product

import androidx.lifecycle.MutableLiveData
import com.upc.stockvision.infrastructure.extensions.LCEState
import com.upc.stockvision.presentation.BaseViewModel
import com.upc.stockvision.presentation.IViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed class IncomingProductState{

}

@HiltViewModel
class IncomingProductViewModel @Inject constructor(): BaseViewModel<LCEState<IncomingProductState>, IncomingProductState>(),
    IViewModel<IncomingProductState> {

    override val renderState: MutableLiveData<LCEState<IncomingProductState>>
        get() = getLiveData()
}
package com.upc.stockvision.presentation.ui.detail_product

import androidx.lifecycle.MutableLiveData
import com.upc.stockvision.infrastructure.extensions.LCEState
import com.upc.stockvision.presentation.BaseViewModel
import com.upc.stockvision.presentation.IViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed class DetailProductState{

}

@HiltViewModel
class DetailProductViewModel @Inject constructor(): BaseViewModel<LCEState<DetailProductState>, DetailProductState>(),
    IViewModel<DetailProductState> {

    override val renderState: MutableLiveData<LCEState<DetailProductState>>
        get() = getLiveData()
}
package com.upc.stockvision.presentation.ui.product_registration

import androidx.lifecycle.MutableLiveData
import com.upc.stockvision.infrastructure.extensions.LCEState
import com.upc.stockvision.presentation.BaseViewModel
import com.upc.stockvision.presentation.IViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed class ProductRegistrationState{

}

@HiltViewModel
class ProductRegistrationViewModel @Inject constructor(): BaseViewModel<LCEState<ProductRegistrationState>, ProductRegistrationState>(),
    IViewModel<ProductRegistrationState> {

    override val renderState: MutableLiveData<LCEState<ProductRegistrationState>>
        get() = getLiveData()
}
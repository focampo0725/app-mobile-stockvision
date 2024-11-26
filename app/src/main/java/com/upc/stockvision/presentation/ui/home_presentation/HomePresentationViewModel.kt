package com.upc.stockvision.presentation.ui.home_presentation

import androidx.lifecycle.MutableLiveData
import com.upc.stockvision.infrastructure.extensions.LCEState
import com.upc.stockvision.presentation.BaseViewModel
import com.upc.stockvision.presentation.IViewModel
import com.upc.stockvision.presentation.ui.reserve_space.ReserveSpaceState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


sealed class HomePresentationState{

}

@HiltViewModel
class HomePresentationViewModel @Inject constructor(): BaseViewModel<LCEState<HomePresentationState>, HomePresentationState>(),
    IViewModel<HomePresentationState> {

    override val renderState: MutableLiveData<LCEState<HomePresentationState>>
        get() = getLiveData()
}
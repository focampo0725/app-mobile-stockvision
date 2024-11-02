package com.upc.stockvision.presentation.ui.reserve_space

import androidx.lifecycle.MutableLiveData
import com.upc.stockvision.infrastructure.extensions.LCEState
import com.upc.stockvision.presentation.BaseViewModel
import com.upc.stockvision.presentation.IViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed class ReserveSpaceState{

}

@HiltViewModel
class ReserveSpaceViewModel @Inject constructor(): BaseViewModel<LCEState<ReserveSpaceState>, ReserveSpaceState>(),
    IViewModel<ReserveSpaceState> {

    override val renderState: MutableLiveData<LCEState<ReserveSpaceState>>
        get() = getLiveData()
}
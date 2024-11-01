package com.upc.stockvision.presentation.ui.home

import androidx.lifecycle.MutableLiveData
import com.upc.stockvision.infrastructure.extensions.LCEState
import com.upc.stockvision.presentation.BaseViewModel
import com.upc.stockvision.presentation.IViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


sealed class HomeSatate {

}

@HiltViewModel
class HomeViewModel @Inject constructor(): BaseViewModel<LCEState<HomeSatate>, HomeSatate>(),
    IViewModel<HomeSatate> {

    override val renderState: MutableLiveData<LCEState<HomeSatate>>
        get() = getLiveData()
}
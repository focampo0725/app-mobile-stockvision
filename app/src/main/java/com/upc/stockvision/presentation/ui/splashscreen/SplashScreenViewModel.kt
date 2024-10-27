package com.upc.stockvision.presentation.ui.splashscreen

import androidx.lifecycle.MutableLiveData
import com.upc.stockvision.infrastructure.extensions.LCEState
import com.upc.stockvision.presentation.BaseViewModel
import com.upc.stockvision.presentation.IViewModel
import javax.inject.Inject

sealed class SplashScreenState {

}

class SplashScreenViewModel @Inject constructor():
    BaseViewModel<LCEState<SplashScreenState>, SplashScreenState>(),IViewModel<SplashScreenState>{


    override val renderState: MutableLiveData<LCEState<SplashScreenState>>
        get() = getLiveData()

}
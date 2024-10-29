package com.upc.stockvision.presentation.ui.sign_up

import androidx.lifecycle.MutableLiveData
import com.upc.stockvision.infrastructure.extensions.LCEState
import com.upc.stockvision.presentation.BaseViewModel
import com.upc.stockvision.presentation.IViewModel
import com.upc.stockvision.presentation.ui.splashscreen.SplashScreenState
import javax.inject.Inject

sealed class SignUpState {

}
class SignUpViewModel @Inject constructor(): BaseViewModel<LCEState<SignUpState>, SignUpState>(),
    IViewModel<SignUpState> {

    override val renderState: MutableLiveData<LCEState<SignUpState>>
        get() = getLiveData()
}
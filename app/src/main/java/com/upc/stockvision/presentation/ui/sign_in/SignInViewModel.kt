package com.upc.stockvision.presentation.ui.sign_in

import androidx.lifecycle.MutableLiveData
import com.upc.stockvision.infrastructure.extensions.LCEState
import com.upc.stockvision.presentation.BaseViewModel
import com.upc.stockvision.presentation.IViewModel
import com.upc.stockvision.presentation.ui.splashscreen.SplashScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed class SignInState {

}

@HiltViewModel
class SignInViewModel @Inject constructor():
    BaseViewModel<LCEState<SignInState>, SignInState>(), IViewModel<SignInState> {


    override val renderState: MutableLiveData<LCEState<SignInState>>
        get() = getLiveData()

}
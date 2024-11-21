package com.upc.stockvision.presentation.ui.splashscreen

import androidx.lifecycle.MutableLiveData
import com.upc.stockvision.data.repository.StockVisionRepository
import com.upc.stockvision.infrastructure.extensions.LCEState
import com.upc.stockvision.presentation.BaseViewModel
import com.upc.stockvision.presentation.IViewModel
import com.upc.stockvision.presentation.ui.home.HomeActivity
import com.upc.stockvision.presentation.ui.home.HomeViewModel
import com.upc.stockvision.presentation.ui.sign_in.SignInActivity
import javax.inject.Inject

sealed class SplashScreenState {
    class LoadActivity(val cls: Class<*>) : SplashScreenState()

}

class SplashScreenViewModel @Inject constructor(val stockVisionRepository: StockVisionRepository):
    BaseViewModel<LCEState<SplashScreenState>, SplashScreenState>(),IViewModel<SplashScreenState>{

    fun loadScreen(){
        val toGoSignIn: () -> Unit = {
            super.postMessage(SplashScreenState.LoadActivity(SignInActivity::class.java))
        }

        if (!stockVisionRepository.spf.isAuthenticateUser) {
            toGoSignIn.invoke()
        }else{
            super.postMessage(SplashScreenState.LoadActivity(HomeActivity::class.java))
        }
    }


    override val renderState: MutableLiveData<LCEState<SplashScreenState>>
        get() = getLiveData()

}
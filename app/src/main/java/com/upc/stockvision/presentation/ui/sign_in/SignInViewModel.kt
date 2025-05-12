package com.upc.stockvision.presentation.ui.sign_in

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.upc.stockvision.data.repository.StockVisionRepository
import com.upc.stockvision.infrastructure.extensions.*
import com.upc.stockvision.presentation.BaseViewModel
import com.upc.stockvision.presentation.IViewModel
import com.upc.stockvision.presentation.ui.splashscreen.SplashScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

sealed class SignInState {
    class SuccessfulAuthentication(val message: String) : SignInState()
    class FailderAuthentication(val message: String) : SignInState()

}

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class SignInViewModel @Inject constructor(private val stockVisionRepository: StockVisionRepository):
    BaseViewModel<LCEState<SignInState>, SignInState>(), IViewModel<SignInState> {

    @Inject
    @ApplicationContext
    lateinit var context: Context
    fun requestUserAuthentication(identityDocument : String, password : String){
       doAsynTask({
           Log.d("[FrancoTest]", "identityUser: ${identityDocument}, password: $password")
           val user = stockVisionRepository.identityUserDao.validateUser(identityDocument, password)
           Log.d("[FrancoTest]", "User found: $user")
           user
       },{
           if (it != null) {
               renderState.postValue(LCEState.Content(SignInState.SuccessfulAuthentication(it.name)))
           }

       },{
           renderState.postValue(LCEState.Content(SignInState.FailderAuthentication("Usuario no encontrado")))
       })
        //ConsumoAPI
//        stockVisionRepository.managementApi.getUser(identityDocument,password).applySchedulers().subscribeApp(
//            onSuccess = {
//                        if (it.isValid){
//                            stockVisionRepository.spf.isAuthenticateUser = true
//                            renderState.value = LCEState.Content(SignInState.SuccessfulAuthentication(it.content.name))
//                        }else{
//                            context.toast(it.exceptions)
//                        }
//
//            }, onError = {
//                context.toast("Hola Error : $it")
//            }
//        )
        //EndConsumoAPI

    }


    override val renderState: MutableLiveData<LCEState<SignInState>>
        get() = getLiveData()

}
package com.upc.stockvision.presentation.ui.sign_up

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.upc.stockvision.data.repository.StockVisionRepository
import com.upc.stockvision.infrastructure.extensions.LCEState
import com.upc.stockvision.infrastructure.extensions.doAsynTask
import com.upc.stockvision.infrastructure.extensions.toast
import com.upc.stockvision.presentation.BaseViewModel
import com.upc.stockvision.presentation.IViewModel
import com.upc.stockvision.presentation.ui.sign_in.SignInState
import com.upc.stockvision.presentation.ui.splashscreen.SplashScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

sealed class SignUpState {
    class SuccessfulSearchUser(val name: String , val surnames: String) : SignUpState()
    class FailedSearchUser(val message : String) : SignUpState()
}

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class SignUpViewModel @Inject constructor(private val stockVisionRepository: StockVisionRepository): BaseViewModel<LCEState<SignUpState>, SignUpState>(),
    IViewModel<SignUpState> {

    @Inject
    @ApplicationContext
    lateinit var context: Context
    fun searchUser(identityDocument : String){
        doAsynTask({
            val user = stockVisionRepository.identityUserDao.searchUser(identityDocument)
            user
        },{
            if (it != null) {
                val name = it.name
                val surname = "${it.fhaterSurname} ${it.motherSurname}"
                renderState.postValue(LCEState.Content(SignUpState.SuccessfulSearchUser(name,surname)))
            } else {
                renderState.postValue(LCEState.Content(SignUpState.FailedSearchUser("Usuario no existe")))
            }
        },{
            renderState.postValue(LCEState.Content(SignUpState.FailedSearchUser("Usuario no existe")))
        })
    }

    override val renderState: MutableLiveData<LCEState<SignUpState>>
        get() = getLiveData()
}
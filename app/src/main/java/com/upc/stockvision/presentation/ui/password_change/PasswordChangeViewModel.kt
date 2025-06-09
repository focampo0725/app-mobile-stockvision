package com.upc.stockvision.presentation.ui.password_change

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
import com.upc.stockvision.presentation.ui.sign_up.SignUpState
import com.upc.stockvision.presentation.ui.splashscreen.SplashScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

sealed class PasswordChangeState {
    class SuccessfulSearchUser(val name: String , val surnames: String) : PasswordChangeState()
    class FailedSearchUser(val message : String) : PasswordChangeState()
}

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class PasswordChangeViewModel @Inject constructor(private val stockVisionRepository: StockVisionRepository): BaseViewModel<LCEState<PasswordChangeState>, PasswordChangeState>(),
    IViewModel<PasswordChangeState> {

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
                renderState.postValue(LCEState.Content(PasswordChangeState.SuccessfulSearchUser(name,surname)))
            } else {
                renderState.postValue(LCEState.Content(PasswordChangeState.FailedSearchUser("Usuario no existe")))
            }
        },{
            renderState.postValue(LCEState.Content(PasswordChangeState.FailedSearchUser("Usuario no existe")))
        })
    }

    override val renderState: MutableLiveData<LCEState<PasswordChangeState>>
        get() = getLiveData()
}
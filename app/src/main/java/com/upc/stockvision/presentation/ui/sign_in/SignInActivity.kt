package com.upc.stockvision.presentation.ui.sign_in

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import com.upc.stockvision.databinding.ActivitySignInBinding
import com.upc.stockvision.infrastructure.extensions.showCustomToast
import com.upc.stockvision.infrastructure.extensions.toast
import com.upc.stockvision.infrastructure.utils.SelectedIcon
import com.upc.stockvision.presentation.BaseActivity
import com.upc.stockvision.presentation.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : BaseActivity<SignInViewModel,SignInState>() {
    private  val viewModel: SignInViewModel by viewModels()
    private lateinit var binding: ActivitySignInBinding
    override fun processRenderState(renderState: SignInState, context: Context) {
        when(renderState){
            is SignInState.SuccessfulAuthentication -> {
                showCustomToast(this,"Bienvenido ---",SelectedIcon.SUCCESS)
                onNextActivity(HomeActivity::class.java,null,true)
            }
            else -> {}
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel(viewModel)
        init()
    }

    fun init(){
        binding.btnIngresar.setOnClickListener {
            val identityUser = binding.etUserIdentity.toString()
            val password = binding.etPassword.toString()
            viewModel.requestUserAuthentication(identityUser, password)
        }
    }
}
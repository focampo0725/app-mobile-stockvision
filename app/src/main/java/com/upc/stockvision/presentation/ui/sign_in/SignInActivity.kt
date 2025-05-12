package com.upc.stockvision.presentation.ui.sign_in

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.upc.stockvision.databinding.ActivitySignInBinding
import com.upc.stockvision.infrastructure.extensions.showCustomToast
import com.upc.stockvision.infrastructure.extensions.toast
import com.upc.stockvision.infrastructure.utils.SelectedIcon
import com.upc.stockvision.presentation.BaseActivity
import com.upc.stockvision.presentation.ui.home.HomeActivity
import com.upc.stockvision.presentation.ui.sign_up.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : BaseActivity<SignInViewModel,SignInState>() {
    private  val viewModel: SignInViewModel by viewModels()
    private lateinit var binding: ActivitySignInBinding
    override fun processRenderState(renderState: SignInState, context: Context) {
        when(renderState){
            is SignInState.SuccessfulAuthentication -> {
                showCustomToast("Bienvenido ${renderState.message}",SelectedIcon.SUCCESS)
                onNextActivity(HomeActivity::class.java,null,true)
            }
            is SignInState.FailderAuthentication ->{
                showCustomToast("Erro: ${renderState.message}",SelectedIcon.WARNING)
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
        binding.tvRecoverdPassword.setOnClickListener {
            showCustomToast("Not Implemented",SelectedIcon.WARNING)
        }
        binding.btnRegister.setOnClickListener {
            onNextActivity(SignUpActivity::class.java,null,true)
        }
        binding.btnIngresar.setOnClickListener {
            val identityUser = binding.etUserIdentity.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            Log.d("[FrancoTest]", "identityUser: ${identityUser}, password: $password")
            viewModel.requestUserAuthentication(identityUser, password)
        }
    }
}
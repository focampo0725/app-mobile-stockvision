package com.upc.stockvision.presentation.ui.sign_up

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.upc.stockvision.databinding.ActivitySignUpBinding
import com.upc.stockvision.infrastructure.extensions.showCustomToast
import com.upc.stockvision.infrastructure.extensions.toast
import com.upc.stockvision.infrastructure.utils.SelectedIcon
import com.upc.stockvision.presentation.BaseActivity
import com.upc.stockvision.presentation.ui.sign_in.SignInActivity
import com.upc.stockvision.presentation.ui.sign_in.SignInState
import com.upc.stockvision.presentation.ui.splashscreen.SplashScreenState
import com.upc.stockvision.presentation.ui.splashscreen.SplashScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : BaseActivity<SignUpViewModel, SignUpState>() {
    private val viewModel: SignUpViewModel by viewModels()
    private lateinit var binding: ActivitySignUpBinding
    override fun processRenderState(renderState: SignUpState, context: Context) {
        when(renderState){
            is SignUpState.SuccessfulSearchUser ->{
                binding.tvNames.text = renderState.name
                binding.tvSurnames.text = renderState.surnames
            }
            is SignUpState.FailedSearchUser ->{
                showCustomToast(renderState.message,SelectedIcon.WARNING)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel(viewModel)
        immersiveScreen(window)
        onInit()
    }

    fun onInit(){
        binding.btnSearchUser.setOnClickListener {
            val identityUser = binding.etUserIdentity.text.toString().trim()
            viewModel.searchUser(identityUser)
        }

        binding.tvSignIn.setOnClickListener {
            onNextActivity(SignInActivity::class.java,null,true)
        }

        binding.btnIngresar.setOnClickListener {
            validateData()
        }
//        validateData()
    }

    fun validateData(){
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()
        val Name = binding.tvNames.text.toString()
        val surName = binding.tvSurnames.text.toString()
        if (password.isEmpty() || confirmPassword.isEmpty() || Name.isEmpty() || surName.isEmpty()) {
            toast("Por favor, llena todos los campos")
            return
        }
        if (password == confirmPassword) {
            showCustomToast("Usuario Registrado",SelectedIcon.SUCCESS)
            onNextActivity(SignInActivity::class.java,null,true)
        } else {
            showCustomToast("Erro en la contraseña",SelectedIcon.WARNING)
        }
    }
}
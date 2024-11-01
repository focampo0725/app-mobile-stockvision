package com.upc.stockvision.presentation.ui.sign_up

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.upc.stockvision.databinding.ActivitySignUpBinding
import com.upc.stockvision.presentation.BaseActivity
import com.upc.stockvision.presentation.ui.splashscreen.SplashScreenState
import com.upc.stockvision.presentation.ui.splashscreen.SplashScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : BaseActivity<SignUpViewModel, SignUpState>() {
    private val viewModel: SignUpViewModel by viewModels()
    private lateinit var binding: ActivitySignUpBinding
    override fun processRenderState(renderState: SignUpState, context: Context) {
        TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel(viewModel)
    }
}
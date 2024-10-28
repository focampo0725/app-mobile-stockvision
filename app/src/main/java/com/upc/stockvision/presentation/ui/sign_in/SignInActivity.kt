package com.upc.stockvision.presentation.ui.sign_in

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import com.upc.stockvision.databinding.ActivitySignInBinding
import com.upc.stockvision.presentation.BaseActivity

class SignInActivity : BaseActivity<SignInViewModel,SignInState>() {
    private  val viewModel: SignInViewModel by viewModels()
    private lateinit var binding: ActivitySignInBinding
    override fun processRenderState(renderState: SignInState, context: Context) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel(viewModel)
    }
}
package com.upc.stockvision.presentation.ui.password_change

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.upc.stockvision.databinding.ActivityPasswordChangeBinding
import com.upc.stockvision.databinding.ActivitySignUpBinding
import com.upc.stockvision.infrastructure.extensions.showCustomToast
import com.upc.stockvision.infrastructure.extensions.toast
import com.upc.stockvision.infrastructure.utils.SelectedIcon
import com.upc.stockvision.presentation.BaseActivity
import com.upc.stockvision.presentation.ui.sign_in.SignInActivity
import com.upc.stockvision.presentation.ui.sign_up.SignUpState
import com.upc.stockvision.presentation.ui.sign_up.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordChangeActivity : BaseActivity<PasswordChangeViewModel, PasswordChangeState>() {
        private val viewModel: PasswordChangeViewModel by viewModels()
        private lateinit var binding: ActivityPasswordChangeBinding
        override fun processRenderState(renderState: PasswordChangeState, context: Context) {
            when(renderState){
                is PasswordChangeState.SuccessfulSearchUser ->{
                    binding.tvNamesChange.text = renderState.name
                    binding.tvSurnamesChange.text = renderState.surnames
                }
                is PasswordChangeState.FailedSearchUser ->{
                    showCustomToast(renderState.message, SelectedIcon.WARNING)
                }
                is PasswordChangeState.UpdatePassword ->{

                }
                else -> {}
            }
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityPasswordChangeBinding.inflate(layoutInflater)
            setContentView(binding.root)
            setupViewModel(viewModel)
            onInit()
        }

        fun onInit(){
            binding.btnSearchUserChange.setOnClickListener {
                val identityUser = binding.etUserIdentityChange.text.toString().trim()
                viewModel.searchUser(identityUser)
            }

            binding.tvSignInChange.setOnClickListener {
                onNextActivity(SignInActivity::class.java,null,true)
            }

            binding.btnConfirmChange.setOnClickListener {
                viewModel.updatePasswordIfValid(binding.etUserIdentityChange.text.toString().trim(),binding.etPasswordChange.text.toString().trim(),binding.etConfirmPasswordChange.text.toString().trim())
            }

        }


    }
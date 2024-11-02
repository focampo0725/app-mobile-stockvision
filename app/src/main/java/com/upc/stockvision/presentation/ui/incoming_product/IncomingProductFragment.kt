package com.upc.stockvision.presentation.ui.incoming_product

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.upc.stockvision.R
import com.upc.stockvision.databinding.FragmentIncomingProductBinding
import com.upc.stockvision.databinding.FragmentProductRegistrationBinding
import com.upc.stockvision.presentation.BaseFragment
import com.upc.stockvision.presentation.ui.product_registration.ProductRegistrationState
import com.upc.stockvision.presentation.ui.product_registration.ProductRegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class IncomingProductFragment @Inject constructor() : BaseFragment<IncomingProductViewModel, IncomingProductState>() {
    val viewModel: ProductRegistrationViewModel by viewModels()
    private lateinit var binding: FragmentIncomingProductBinding

    override fun processRenderState(renderState: IncomingProductState, context: Context) {
        TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIncomingProductBinding.inflate(inflater, container,false)
        return binding.root
    }

}
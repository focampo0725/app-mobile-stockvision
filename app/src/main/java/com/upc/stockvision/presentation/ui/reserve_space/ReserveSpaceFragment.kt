package com.upc.stockvision.presentation.ui.reserve_space

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.upc.stockvision.R
import com.upc.stockvision.databinding.FragmentIncomingProductBinding
import com.upc.stockvision.databinding.FragmentReserveSpaceBinding
import com.upc.stockvision.presentation.BaseFragment
import com.upc.stockvision.presentation.ui.product_registration.ProductRegistrationViewModel

class ReserveSpaceFragment : BaseFragment<ReserveSpaceViewModel, ReserveSpaceState>() {
    val viewModel: ReserveSpaceViewModel by viewModels()
    private lateinit var binding: FragmentReserveSpaceBinding

    override fun processRenderState(renderState: ReserveSpaceState, context: Context) {
        TODO("Not yet implemented")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReserveSpaceBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel(viewModel = viewModel)
    }

}
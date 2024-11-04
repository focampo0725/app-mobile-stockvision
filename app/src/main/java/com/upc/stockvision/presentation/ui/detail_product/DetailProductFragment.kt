package com.upc.stockvision.presentation.ui.detail_product

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.upc.stockvision.R
import com.upc.stockvision.databinding.FragmentDetailProductBinding
import com.upc.stockvision.databinding.FragmentInventoryControlBinding
import com.upc.stockvision.presentation.BaseFragment
import com.upc.stockvision.presentation.ui.inventory_control.InventoryControlState
import com.upc.stockvision.presentation.ui.inventory_control.InventoryControlViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailProductFragment : BaseFragment<DetailProductViewModel, DetailProductState>() {

    val viewModel: DetailProductViewModel by viewModels()
    private lateinit var binding : FragmentDetailProductBinding
    override fun processRenderState(renderState: DetailProductState, context: Context) {
        TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailProductBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel(viewModel = viewModel)


    }


}
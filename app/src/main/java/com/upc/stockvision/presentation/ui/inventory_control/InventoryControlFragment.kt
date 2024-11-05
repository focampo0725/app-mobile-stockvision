package com.upc.stockvision.presentation.ui.inventory_control

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.upc.stockvision.R
import com.upc.stockvision.databinding.FragmentInventoryControlBinding
import com.upc.stockvision.databinding.FragmentProductRegistrationBinding
import com.upc.stockvision.presentation.BaseFragment
import com.upc.stockvision.presentation.ui.product_registration.ProductRegistrationViewModel


class InventoryControlFragment : BaseFragment<InventoryControlViewModel,InventoryControlState>() {

    val viewModel: InventoryControlViewModel by viewModels()
    private lateinit var binding : FragmentInventoryControlBinding
    override fun processRenderState(renderState: InventoryControlState, context: Context) {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInventoryControlBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel(viewModel = viewModel)
        

        }


}
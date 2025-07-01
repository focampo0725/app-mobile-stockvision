package com.upc.stockvision.presentation.ui.detail_reserve_space

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.upc.stockvision.R
import com.upc.stockvision.databinding.FragmentDetailProductBinding
import com.upc.stockvision.databinding.FragmentDetailReserveBinding
import com.upc.stockvision.infrastructure.AppState
import com.upc.stockvision.presentation.BaseFragment
import com.upc.stockvision.presentation.ui.detail_product.DetailProductState
import com.upc.stockvision.presentation.ui.detail_product.DetailProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class DetailReserveFragment @Inject constructor(val appState: AppState): BaseFragment<DetailReserveViewModel, DetailReserveState>() {
    val viewModel: DetailReserveViewModel by viewModels()
    private lateinit var binding : FragmentDetailReserveBinding
    override fun processRenderState(renderState: DetailReserveState, context: Context) {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailReserveBinding.inflate(inflater, container,false)
        return binding.root
    }


}
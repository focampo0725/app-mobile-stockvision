package com.upc.stockvision.presentation.ui.home_presentation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.upc.stockvision.R
import com.upc.stockvision.databinding.FragmentHomePresentationBinding
import com.upc.stockvision.databinding.FragmentReserveSpaceBinding
import com.upc.stockvision.presentation.BaseFragment
import com.upc.stockvision.presentation.ui.reserve_space.ReserveSpaceState
import com.upc.stockvision.presentation.ui.reserve_space.ReserveSpaceViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomePresentationFragment @Inject constructor() : BaseFragment<HomePresentationViewModel, HomePresentationState>() {
    val viewModel: HomePresentationViewModel by viewModels()
    private lateinit var binding: FragmentHomePresentationBinding
    override fun processRenderState(renderState: HomePresentationState, context: Context) {
        TODO("Not yet implemented")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePresentationBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel(viewModel = viewModel)
    }

}
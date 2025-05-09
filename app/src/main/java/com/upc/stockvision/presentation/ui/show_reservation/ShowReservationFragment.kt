package com.upc.stockvision.presentation.ui.show_reservation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.upc.stockvision.R
import com.upc.stockvision.databinding.FragmentReserveSpaceBinding
import com.upc.stockvision.databinding.FragmentShowReservationBinding
import com.upc.stockvision.domain.dto.ProductOnDetailDTO
import com.upc.stockvision.domain.dto.ReserveAreaDTO
import com.upc.stockvision.infrastructure.extensions.toast
import com.upc.stockvision.presentation.BaseFragment
import com.upc.stockvision.presentation.adapter.ProductAdapter
import com.upc.stockvision.presentation.adapter.ReserveAdapter
import com.upc.stockvision.presentation.ui.reserve_space.ReserveSpaceState
import com.upc.stockvision.presentation.ui.reserve_space.ReserveSpaceViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ShowReservationFragment @Inject constructor() : BaseFragment<ShowReservationViewModel, ShowReservationState>() {

    val viewModel: ShowReservationViewModel by viewModels()
    private lateinit var binding: FragmentShowReservationBinding

    private lateinit var reservationList: List<ReserveAreaDTO>
    private lateinit var reserveAdapter: ReserveAdapter

    override fun processRenderState(renderState: ShowReservationState, context: Context) {
        when(renderState){
            is ShowReservationState.ReserveLoaded ->{
                reservationList = renderState.reserveList
                reserveAdapter.setReserves(reservationList)
            }

            else -> {}
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowReservationBinding.inflate(inflater, container,false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel(viewModel = viewModel)

        binding.rvReserve.layoutManager = LinearLayoutManager(requireContext())
        reserveAdapter = ReserveAdapter(requireContext()) {

            context?.toast("${it.productName} ")
        }

        binding.rvReserve.adapter = reserveAdapter
        initView()
    }

    fun initView(){
        viewModel.requestReserveList()
    }

}
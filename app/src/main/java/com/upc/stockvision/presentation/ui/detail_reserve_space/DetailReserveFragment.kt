package com.upc.stockvision.presentation.ui.detail_reserve_space

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.upc.stockvision.databinding.FragmentDetailReserveBinding
import com.upc.stockvision.domain.dto.ReservationDetailDTO
import com.upc.stockvision.infrastructure.AppState
import com.upc.stockvision.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class DetailReserveFragment @Inject constructor(val appState: AppState): BaseFragment<DetailReserveViewModel, DetailReserveState>() {
    val viewModel: DetailReserveViewModel by viewModels()
    private lateinit var binding : FragmentDetailReserveBinding
    private lateinit var reserve: ReservationDetailDTO
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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel(viewModel = viewModel)
        initView()

    }

    fun initView(){
        loadReserveDetailsData()
    }
    fun base64ToBitmap(base64String: String): Bitmap? {
        return try {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    private fun loadReserveDetailsData() {
        binding.tvProductName.text = ""
        binding.tvWarehouse.text = ""
        binding.tvAreaWarehouse.text = ""
        binding.tvArrivalDate.text = ""
        binding.tvQuantity.text = ""
        val bitmap = base64ToBitmap("")
        binding.ivProductPhotoPhoto.setImageBitmap(bitmap)
    }

}
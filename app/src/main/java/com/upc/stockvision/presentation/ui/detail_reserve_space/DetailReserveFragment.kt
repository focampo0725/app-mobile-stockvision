package com.upc.stockvision.presentation.ui.detail_reserve_space

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.upc.stockvision.databinding.FragmentDetailReserveBinding
import com.upc.stockvision.domain.dto.ProductOnDetailDTO
import com.upc.stockvision.domain.dto.ReservationDetailDTO
import com.upc.stockvision.infrastructure.AppState
import com.upc.stockvision.infrastructure.utils.Constants
import com.upc.stockvision.presentation.BaseFragment
import com.upc.stockvision.presentation.dialog.DialogAreaWarehouse
import com.upc.stockvision.presentation.dialog.DialogConfirmation
import com.upc.stockvision.presentation.dialog.OnDialogListener
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
        arguments?.let {
            reserve = it.getSerializable(Constants.RESERVE_KEY) as ReservationDetailDTO
        }
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
        binding.btnBack.setOnClickListener {
            appState.onDrawShowReserve?.invoke()
        }
        binding.btnMakeReservation.setOnClickListener {
            DialogConfirmation("¿Estás seguro que deseas continuar?", object : OnDialogListener {
                override fun onAceptar() {
                    Toast.makeText(requireContext(), "Confirmado", Toast.LENGTH_SHORT).show()

                }
            }).show(parentFragmentManager, DialogConfirmation.TAG)

        }
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
        binding.tvProductName.text = reserve.productName
        binding.tvWarehouse.text = reserve.warehouseName
        binding.tvAreaWarehouse.text = reserve.areaWarehouseName
        binding.tvArrivalDate.text = reserve.arrivalDate.toString()
        binding.tvQuantity.text = reserve.quantityReserved.toString()
        val bitmap = base64ToBitmap(reserve.photo)
        binding.ivProductPhotoPhoto.setImageBitmap(bitmap)
    }

}
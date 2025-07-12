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
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.upc.stockvision.R
import com.upc.stockvision.databinding.FragmentDetailReserveBinding
import com.upc.stockvision.domain.dto.ProductOnDetailDTO
import com.upc.stockvision.domain.dto.ReservationDetailDTO
import com.upc.stockvision.domain.entities.ReserveArea.Companion.CANCEL_RESERVE
import com.upc.stockvision.domain.entities.ReserveArea.Companion.CONFIRM_RESERVE
import com.upc.stockvision.infrastructure.AppState
import com.upc.stockvision.infrastructure.extensions.toast
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
        when(renderState){
            is DetailReserveState.ReserveState ->{
                context.toast(renderState.messageConfirm)
            }
            is DetailReserveState.ReserveStateShow -> {
                when(renderState.state){
                    CANCEL_RESERVE -> {
                            binding.btnMakeReservation.visibility = View.GONE
                            binding.btnCancelReservation.visibility = View.GONE
                            binding.tvReservationStatus.text ="CANCELADO"
                            binding.tvReservationStatus.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.alert_error))
                            binding.tvReservationStatus.visibility = View.VISIBLE

                    }
                    CONFIRM_RESERVE -> {
                        binding.btnMakeReservation.visibility = View.GONE
                        binding.btnCancelReservation.visibility = View.GONE
                        binding.tvReservationStatus.text ="CONCRETADO"
                        binding.tvReservationStatus.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.alert_success))
                        binding.tvReservationStatus.visibility = View.VISIBLE
                    }
                }
            }
        }
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
        viewModel.stateReserve(reserve.reservationId)

    }

    fun initView(){
        loadReserveDetailsData()
        binding.btnBack.setOnClickListener {
            appState.onDrawShowReserve?.invoke()
        }
        binding.btnMakeReservation.setOnClickListener {
            DialogConfirmation("¿Quiere confirmar el Ingreso?", object : OnDialogListener {
                override fun onAceptar() {
                    viewModel.MakeReservation(reserveId= reserve.reservationId , productId = reserve.productCode, destinationAreaId = reserve.areaWarehouseCode, amountArrived = reserve.quantityReserved, typeMovement = "Ingreso")

                }
            }).show(parentFragmentManager, DialogConfirmation.TAG)

        }

        binding.btnCancelReservation.setOnClickListener {
            DialogConfirmation("¿Estás seguro que deseas Cancelar?", object : OnDialogListener {
                override fun onAceptar() {
                    viewModel.cancelReservation(reserveId= reserve.reservationId)

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
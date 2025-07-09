package com.upc.stockvision.presentation.ui.detail_product_movement

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.upc.stockvision.R
import com.upc.stockvision.databinding.FragmentDetailProductMovementBinding
import com.upc.stockvision.databinding.FragmentDetailReserveBinding
import com.upc.stockvision.domain.dto.ProductMovementDTO
import com.upc.stockvision.domain.dto.ProductOnDetailDTO
import com.upc.stockvision.infrastructure.AppState
import com.upc.stockvision.infrastructure.utils.Constants
import com.upc.stockvision.presentation.BaseFragment
import com.upc.stockvision.presentation.ui.detail_reserve_space.DetailReserveState
import com.upc.stockvision.presentation.ui.detail_reserve_space.DetailReserveViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailProductMovementFragment @Inject constructor(val appState: AppState): BaseFragment<DetailProductMovementViewModel, DetailProductMovementState>() {
    val viewModel: DetailProductMovementViewModel by viewModels()
    private lateinit var binding : FragmentDetailProductMovementBinding
    private lateinit var productMovementDTO: ProductMovementDTO
    override fun processRenderState(renderState: DetailProductMovementState, context: Context) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productMovementDTO = it.getSerializable(Constants.PRODUCT_MOVEMENT_KEY) as ProductMovementDTO
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailProductMovementBinding.inflate(inflater, container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel(viewModel = viewModel)


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
    private fun loadProductMovementDetailsData() {
        binding.tvProductName.text = productMovementDTO.productName
        binding.tvWarehouse.text = productMovementDTO.initialWarehouse
        binding.tvAreaWarehouse.text = productMovementDTO.initialAreaWarehouse
        binding.tvInitialQuantity.text = productMovementDTO.quantityInitial.toString()
        binding.tvInitialQuantityAfter.text = productMovementDTO.quantityInitialArea.toString()
        binding.tvFinalWarehouse.text = productMovementDTO.finalWarehouse
        binding.tvFinalAreaWarehouse.text = productMovementDTO.finalAreaWarehouse
        binding.tvinitialAmountBeforeMove.text = productMovementDTO.quantityInitialFinalArea.toString()
        binding.tvQuantityMovement.text = productMovementDTO.amountMoved.toString()
        binding.tvTypeMovement.text = productMovementDTO.typeMovement
        binding.tvMovementDate.text = productMovementDTO.movementDate.toString()
        val bitmap = base64ToBitmap(productMovementDTO.photo)
        binding.ivProductPhoto.setImageBitmap(bitmap)
    }


}
package com.upc.stockvision.presentation.ui.product_movement_record

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.upc.stockvision.R
import com.upc.stockvision.databinding.FragmentProductMovementRecordBinding
import com.upc.stockvision.domain.dto.ProductMovementDTO
import com.upc.stockvision.infrastructure.AppState
import com.upc.stockvision.infrastructure.extensions.toast
import com.upc.stockvision.presentation.BaseFragment
import com.upc.stockvision.presentation.adapter.ProductMovementAdapter

import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ProductMovementRecordFragment @Inject constructor(val appState: AppState) : BaseFragment<ProductMovementRecordViewModel, ProductMovementRecordState>() {

    val viewModel: ProductMovementRecordViewModel by viewModels()
    private lateinit var binding: FragmentProductMovementRecordBinding

    private lateinit var productMovementList: List<ProductMovementDTO>
    private lateinit var productMovementAdapter: ProductMovementAdapter



    override fun processRenderState(renderState: ProductMovementRecordState, context: Context) {
        when(renderState){
            is ProductMovementRecordState.ProductMovementLoaded ->{
                productMovementList = renderState.productMovementList
                productMovementAdapter.setReserves(productMovementList)
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
        binding = FragmentProductMovementRecordBinding.inflate(inflater, container,false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel(viewModel = viewModel)
        onInit()
        binding.rvProductMovement.layoutManager = LinearLayoutManager(requireContext())
        productMovementAdapter = ProductMovementAdapter(requireContext()) {
            appState.onDrawProductMovementDetail?.invoke(it)
            context?.toast("${it.productName} ")
        }
        binding.rvProductMovement.adapter = productMovementAdapter
        viewModel.requestProductMovementList()

    }

    fun onInit(){
        binding.btnCalenderStartTime.setOnClickListener {
            mostrarSelectorDeFecha(0)
        }

        binding.btnCalenderEndTime.setOnClickListener {
            mostrarSelectorDeFecha(1)
        }
        binding.btnCreateMovement.setOnClickListener {
            appState.onDrawCreateMovement?.invoke()
        }
    }

    private fun mostrarSelectorDeFecha(type : Int) {
        val calendario = Calendar.getInstance()
        val year = calendario.get(Calendar.YEAR)
        val month = calendario.get(Calendar.MONTH)
        val day = calendario.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.CustomDatePickerTheme,
            { _, año, mesSeleccionado, diaSeleccionado ->
                val fechaSeleccionada = "$diaSeleccionado/${mesSeleccionado + 1}/$año"
                if (type == 0) binding.tvStartTimeReserve.text = fechaSeleccionada else binding.tvEndTimeReserve.text = fechaSeleccionada
            },
            year, month, day
        )

        datePickerDialog.show()
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE)?.apply {
            setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrincipalDatePicker))
        }
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE)?.apply {
            setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrincipalDatePicker))
        }
    }



}
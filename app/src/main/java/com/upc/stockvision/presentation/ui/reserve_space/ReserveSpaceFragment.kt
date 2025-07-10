package com.upc.stockvision.presentation.ui.reserve_space

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.upc.stockvision.R
import com.upc.stockvision.databinding.FragmentIncomingProductBinding
import com.upc.stockvision.databinding.FragmentReserveSpaceBinding
import com.upc.stockvision.infrastructure.AppState
import com.upc.stockvision.infrastructure.extensions.showCustomToast
import com.upc.stockvision.infrastructure.utils.SelectedIcon
import com.upc.stockvision.presentation.BaseFragment
import com.upc.stockvision.presentation.dialog.*
import com.upc.stockvision.presentation.ui.product_registration.ProductRegistrationState
import com.upc.stockvision.presentation.ui.product_registration.ProductRegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ReserveSpaceFragment @Inject constructor(val appState: AppState) : BaseFragment<ReserveSpaceViewModel, ReserveSpaceState>() {
    val viewModel: ReserveSpaceViewModel by viewModels()
    private lateinit var binding: FragmentReserveSpaceBinding

    override fun processRenderState(renderState: ReserveSpaceState, context: Context) {
        when (renderState) {
            is ReserveSpaceState.CategoriesLoaded -> {
                DialogCategory(categotyList = renderState.categoriesList) { selectedCategory ->
                    binding.tvCategory.text = selectedCategory.categoryName
                }.show(parentFragmentManager, DialogCategory.TAG)

            }
            is ReserveSpaceState.ProductLoaded -> {
                DialogProduct(productList = renderState.productList){selectedProduct ->
                    binding.tvProduct.text = selectedProduct.productName

                }.show(parentFragmentManager,DialogProduct.TAG)
            }

            is ReserveSpaceState.WarehouseLoaded -> {
                DialogWarehouse(warehouseList = renderState.warehouseList) { selectedWarehouse ->
                    binding.tvWarehouseReserve.text = selectedWarehouse.warehouseName
                }.show(parentFragmentManager, DialogWarehouse.TAG)
            }
            is ReserveSpaceState.AreaWarehouseLoaded -> {
                DialogAreaWarehouse(areaWarehouseList = renderState.areaWarehouseList) { selectedAreaWarehouse ->
                    binding.tvReserveZone.text = selectedAreaWarehouse.areaWarehouseName
                }.show(parentFragmentManager, DialogAreaWarehouse.TAG)
            }
            is ReserveSpaceState.SuccessReserveRegister ->{
                context.showCustomToast(renderState.message, SelectedIcon.SUCCESS)
                appState.onDrawShowReserve?.invoke()
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
        binding = FragmentReserveSpaceBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel(viewModel = viewModel)
        onInit()
    }

    fun onInit(){
        binding.tvCategory.setOnClickListener {
            viewModel.requestCategoryList()
        }

        binding.tvProduct.setOnClickListener {
            viewModel.requestProductList(binding.tvCategory.text.toString().trim())
        }


        binding.tvWarehouseReserve.setOnClickListener {
            viewModel.requestWarehouse()
        }

        binding.tvReserveZone.setOnClickListener {
            viewModel.requestAreaWarehouse(binding.tvWarehouseReserve.text.toString().trim())
        }

        binding.btnCalender.setOnClickListener {
            mostrarSelectorDeFecha()
        }

        binding.btnRegisterReserve.setOnClickListener {
            val category = binding.tvCategory.text.toString().trim()
            val product = binding.tvProduct.text.toString().trim()
            val quanty = binding.etAmount.text.toString().toInt()
            val warehouse = binding.tvWarehouseReserve.text.toString().trim()
            val areaWarehouse = binding.tvReserveZone.text.toString().trim()
            val createAt = binding.tvStartTimeReserve.text.toString()
            val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val fechaDate: Date? = formato.parse(createAt)
            val days = binding.etDurationDays.text.toString().toInt()
            viewModel.registerReserve(category,product,quanty,warehouse,areaWarehouse,days,fechaDate!!)

        }

    }

    private fun mostrarSelectorDeFecha() {
        val calendario = Calendar.getInstance()
        val year = calendario.get(Calendar.YEAR)
        val month = calendario.get(Calendar.MONTH)
        val day = calendario.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.CustomDatePickerTheme,
            { _, año, mesSeleccionado, diaSeleccionado ->
                val fechaSeleccionada = "$diaSeleccionado/${mesSeleccionado + 1}/$año"
                binding.tvStartTimeReserve.text = fechaSeleccionada
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
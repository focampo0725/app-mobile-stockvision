package com.upc.stockvision.presentation.ui.create_product_movement

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.upc.stockvision.databinding.FragmentCreateProductMovementBinding
import com.upc.stockvision.presentation.BaseFragment
import com.upc.stockvision.presentation.dialog.DialogAreaWarehouse
import com.upc.stockvision.presentation.dialog.DialogCategory
import com.upc.stockvision.presentation.dialog.DialogProduct
import com.upc.stockvision.presentation.dialog.DialogWarehouse
import com.upc.stockvision.presentation.ui.reserve_space.ReserveSpaceState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreateProductMovementFragment @Inject constructor() : BaseFragment<CreateProductMovementViewModel, CreateProductMovementState>() {

    val viewModel: CreateProductMovementViewModel by viewModels()
    private lateinit var binding: FragmentCreateProductMovementBinding
    var cantidadInicial = 0

    override fun processRenderState(renderState: CreateProductMovementState, context: Context) {
        when(renderState){
            is CreateProductMovementState.CategoriesLoaded -> {
                DialogCategory(categotyList = renderState.categoriesList) { selectedCategory ->
                    binding.tvCategory.text = selectedCategory.categoryName
                }.show(parentFragmentManager, DialogCategory.TAG)

            }
            is CreateProductMovementState.ProductLoaded -> {
                DialogProduct(productList = renderState.productList){selectedProduct ->
                    binding.etFinalQuantity.text.clear()
                    binding.etFinalQuantity.hint = "cantidad"
                    binding.tvProduct.text = selectedProduct.productName
                    cantidadInicial = selectedProduct.quantity
                    binding.tvInitialQuantity.text = cantidadInicial.toString()
                    binding.tvInitialWarehouse.text = selectedProduct.warehouse
                    binding.tvInitialAreaWarehouse.text = selectedProduct.areaWarehouse

                }.show(parentFragmentManager, DialogProduct.TAG)
            }

            is CreateProductMovementState.WarehouseLoaded -> {
                DialogWarehouse(warehouseList = renderState.warehouseList) { selectedWarehouse ->
                    binding.tvFinalAreaWarehouse.text = selectedWarehouse.warehouseName
                }.show(parentFragmentManager, DialogWarehouse.TAG)
            }
            is CreateProductMovementState.AreaWarehouseLoaded -> {
                DialogAreaWarehouse(areaWarehouseList = renderState.areaWarehouseList) { selectedAreaWarehouse ->
                    binding.tvFinalAreaWarehouse.text = selectedAreaWarehouse.areaWarehouseName
                }.show(parentFragmentManager, DialogAreaWarehouse.TAG)
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
        binding = FragmentCreateProductMovementBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel(viewModel = viewModel)

        initView()
    }

    fun initView(){
        binding.etFinalQuantity.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val texto = s.toString()
                val cantidadRestar = texto.toIntOrNull() ?: 0

                // Si el EditText está vacío o es 0, mostrar la cantidad inicial
                if (texto.isEmpty() || cantidadRestar == 0) {
                    binding.tvInitialQuantity.text = cantidadInicial.toString()
                } else {
                    val resultado = cantidadInicial - cantidadRestar
                    binding.tvInitialQuantity.text = resultado.toString()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        binding.tvCategory.setOnClickListener {
            viewModel.requestCategoryList()
        }

        binding.tvProduct.setOnClickListener {
            viewModel.requestProductList(binding.tvCategory.text.toString())
        }
        binding.tvFinalWarehouse.setOnClickListener {
            viewModel.requestWarehouse()
        }
        binding.tvFinalAreaWarehouse.setOnClickListener {
            viewModel.requestAreaWarehouse(binding.tvFinalWarehouse.text.toString())
        }
    }


}
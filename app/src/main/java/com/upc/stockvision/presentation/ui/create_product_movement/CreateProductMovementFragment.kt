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
import com.upc.stockvision.infrastructure.AppState
import com.upc.stockvision.infrastructure.extensions.showCustomToast
import com.upc.stockvision.infrastructure.extensions.toast
import com.upc.stockvision.infrastructure.utils.SelectedIcon
import com.upc.stockvision.presentation.BaseFragment
import com.upc.stockvision.presentation.dialog.*
import com.upc.stockvision.presentation.ui.reserve_space.ReserveSpaceState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreateProductMovementFragment @Inject constructor(val appState: AppState) : BaseFragment<CreateProductMovementViewModel, CreateProductMovementState>() {

    val viewModel: CreateProductMovementViewModel by viewModels()
    private lateinit var binding: FragmentCreateProductMovementBinding
    var initialQuantity = 0
    var idProdcutSelected = 0
    var finalAreaWarehouseCode : String ?= null
    var categoryCode : String ?= null

    override fun processRenderState(renderState: CreateProductMovementState, context: Context) {
        when(renderState){
            is CreateProductMovementState.CategoriesLoaded -> {
                DialogCategory(categotyList = renderState.categoriesList) { selectedCategory ->
                    binding.tvCategory.text = selectedCategory.categoryName
                    categoryCode = selectedCategory.codeCategory
                }.show(parentFragmentManager, DialogCategory.TAG)

            }
            is CreateProductMovementState.ProductLoaded -> {
                DialogProduct(productList = renderState.productList){selectedProduct ->
                    binding.etFinalQuantity.text.clear()
                    idProdcutSelected = selectedProduct.idProduct
                    binding.etFinalQuantity.hint = "cantidad"
                    binding.tvProduct.text = selectedProduct.productName
                    initialQuantity = selectedProduct.quantity
                    binding.tvInitialQuantity.text = initialQuantity.toString()
                    binding.tvInitialWarehouse.text = selectedProduct.warehouse
                    binding.tvInitialAreaWarehouse.text = selectedProduct.areaWarehouse

                }.show(parentFragmentManager, DialogProduct.TAG)
            }

            is CreateProductMovementState.WarehouseLoaded -> {
                DialogWarehouse(warehouseList = renderState.warehouseList) { selectedWarehouse ->
                    binding.tvFinalWarehouse.text = selectedWarehouse.warehouseName
                }.show(parentFragmentManager, DialogWarehouse.TAG)
            }
            is CreateProductMovementState.AreaWarehouseLoaded -> {
                DialogAreaWarehouse(areaWarehouseList = renderState.areaWarehouseList) { selectedAreaWarehouse ->
                    binding.tvFinalAreaWarehouse.text = selectedAreaWarehouse.areaWarehouseName
                    finalAreaWarehouseCode = selectedAreaWarehouse.codeAreaWarehouse
                }.show(parentFragmentManager, DialogAreaWarehouse.TAG)
            }
            is CreateProductMovementState.TypeProductMovementLoaded -> {
                DialogTypeMovement(typeProductMovementList = renderState.typeProductMovementList) { selectedtypeMovement ->
                    binding.tvTypeMovement.text = selectedtypeMovement.typeMovementName
                }.show(parentFragmentManager, DialogTypeMovement.TAG)
            }
            is CreateProductMovementState.CreateProductMovementDetails -> {
                context.showCustomToast(renderState.messsage, iconType = SelectedIcon.SUCCESS)
                appState.onDrawMovement?.invoke()
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
                    binding.tvInitialQuantity.text = initialQuantity.toString()
                } else {
                    val resultado = initialQuantity - cantidadRestar
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
            if(binding.tvCategory.text.toString().isEmpty() || categoryCode == null){
                context?.toast("Selecciona primero una categoria")
                return@setOnClickListener
            }
            viewModel.requestProductList(categoryCode!!)

        }
        binding.tvFinalWarehouse.setOnClickListener {
            viewModel.requestWarehouse()
        }
        binding.tvFinalAreaWarehouse.setOnClickListener {
            viewModel.requestAreaWarehouse(binding.tvFinalWarehouse.text.toString())
        }
        binding.tvTypeMovement.setOnClickListener {
            viewModel.reqeustTypeMovement()
        }

        binding.btnCreatNewMovement.setOnClickListener {
//            productId: Int,
//            initialAreaId: String,
//            finalAreaId: String,
//            amountMoved: Int,
//            typeMovement: String
            viewModel.createMovement(idProdcutSelected,"",finalAreaWarehouseCode!!,binding.etFinalQuantity.text.toString().toInt(),binding.tvTypeMovement.text.toString())

        }


    }


}
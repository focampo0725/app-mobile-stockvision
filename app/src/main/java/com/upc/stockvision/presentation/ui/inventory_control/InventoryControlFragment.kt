package com.upc.stockvision.presentation.ui.inventory_control

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.upc.stockvision.infrastructure.utils.Constants
import com.upc.stockvision.R
import com.upc.stockvision.databinding.FragmentInventoryControlBinding
import com.upc.stockvision.databinding.FragmentProductRegistrationBinding
import com.upc.stockvision.domain.dto.ProductDTO
import com.upc.stockvision.domain.dto.ProductOnDetailDTO
import com.upc.stockvision.infrastructure.AppState
import com.upc.stockvision.infrastructure.extensions.toast
import com.upc.stockvision.presentation.BaseFragment
import com.upc.stockvision.presentation.adapter.ProductAdapter
import com.upc.stockvision.presentation.dialog.DialogCategory
import com.upc.stockvision.presentation.dialog.DialogSupplier
import com.upc.stockvision.presentation.dialog.DialogWarehouse
import com.upc.stockvision.presentation.ui.detail_product.DetailProductFragment
import com.upc.stockvision.presentation.ui.product_registration.ProductRegistrationState
import com.upc.stockvision.presentation.ui.product_registration.ProductRegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class InventoryControlFragment @Inject constructor(val appState: AppState): BaseFragment<InventoryControlViewModel,InventoryControlState>() {

    val viewModel: InventoryControlViewModel by viewModels()
    private lateinit var binding : FragmentInventoryControlBinding

    private lateinit var productList: List<ProductOnDetailDTO>
    private lateinit var productAdapter: ProductAdapter
    var categoryCode : String ?= null
    var warehouseCode : String ?= null

    override fun processRenderState(renderState: InventoryControlState, context: Context) {
        when(renderState){
            is InventoryControlState.ProductLoaded -> {
                productList = renderState.productList
                productAdapter.setProduct(productList)
            }
            is InventoryControlState.CategoriesLoaded ->{
                DialogCategory(categotyList = renderState.categoriesList) { selectedCategory ->
                    binding.tvCategory.text = selectedCategory.categoryName
                    val category = binding.tvCategory.text.toString()
                    categoryCode = selectedCategory.codeCategory
                    applyFilter(emptyList(), category, null)
                }.show(parentFragmentManager, DialogCategory.TAG)

            }
            is InventoryControlState.WarehouseLoaded ->{
                DialogWarehouse(warehouseList = renderState.warehouseList){selectedWarehouse ->
                    binding.tvWarehouseControlProduct.text = selectedWarehouse.warehouseName
                    binding.tvWarehouseControlProduct.text.toString()
                    warehouseCode = selectedWarehouse.codeWarehouse
                    viewModel.onWarehouseSelected(selectedWarehouse.codeWarehouse)
//                    applyFilter(warehouse, null, null)
                }.show(parentFragmentManager, DialogWarehouse.TAG)
            }
            is InventoryControlState.AreaWarehouseList ->{

                applyFilter(renderState.areaWarehouseList, null, null)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInventoryControlBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel(viewModel = viewModel)
        binding.rvProducts.layoutManager = LinearLayoutManager(requireContext())
        productAdapter = ProductAdapter(requireContext()) {
            appState.onDrawProductDetail?.invoke(it)
            context?.toast("${it.productName} ")
        }

        binding.rvProducts.adapter = productAdapter
        
        binding.tvCategory.setOnClickListener {
            viewModel.requestCategoryLista()
        }
        binding.tvWarehouseControlProduct.setOnClickListener {
            viewModel.requestWarehouse(categoryCode)
        }
        viewModel.requestProductList()
        setupFilters()
        
        }
    private fun setupFilters() {
        binding.etProductName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val productName = s.toString()
                applyFilter(emptyList(), null, productName)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }


    private fun applyFilter(areaList: List<InventoryControlViewModel.areaWareHouse>, category: String?, productName: String?) {
        productAdapter.filterProducts(areaList, category, productName)
    }


}
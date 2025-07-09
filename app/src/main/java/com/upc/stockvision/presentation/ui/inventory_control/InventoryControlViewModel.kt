package com.upc.stockvision.presentation.ui.inventory_control

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.upc.stockvision.data.repository.StockVisionRepository
import com.upc.stockvision.domain.dto.*
import com.upc.stockvision.infrastructure.extensions.LCEState
import com.upc.stockvision.infrastructure.extensions.doAsynTask
import com.upc.stockvision.presentation.BaseViewModel
import com.upc.stockvision.presentation.IViewModel
import com.upc.stockvision.presentation.ui.product_registration.ProductRegistrationState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

sealed class InventoryControlState{
    class ProductLoaded(val productList : List<ProductOnDetailDTO>) : InventoryControlState()

    class CategoriesLoaded(val categoriesList: ResponseGenericDTO<CategoryDTO>) :
        InventoryControlState()

    class WarehouseLoaded(val warehouseList: ResponseGenericDTO<WarehouseDTO>) :
        InventoryControlState()
}

@HiltViewModel
class InventoryControlViewModel @Inject constructor(val stockVisionRepository: StockVisionRepository): BaseViewModel<LCEState<InventoryControlState>, InventoryControlState>(),
    IViewModel<InventoryControlState> {
    @SuppressLint("StaticFieldLeak")
    @Inject
    @ApplicationContext
    lateinit var context: Context
    fun requestProductList() {
        doAsynTask({
            stockVisionRepository.productsDao.getAllProductDetails()
        }, {
            renderState.value = LCEState.Content(InventoryControlState.ProductLoaded(it))
        })
    }


    fun requestWarehouse(categoryCode: String? = null) {
        doAsynTask({
            val warehouses = if (categoryCode.isNullOrEmpty()) {
                stockVisionRepository.warehouseDao.getAll()
            } else {
                stockVisionRepository.warehouseDao.getWarehousesByCategory(categoryCode)
            }

            warehouses.map { warehouse ->
                WarehouseDTO(
                    codeWarehouse = warehouse.warehouseCode,
                    warehouseName = warehouse.warehouseName
                )
            }
        }, {
            val response = ResponseGenericDTO(content = it, isValid = true, exceptions = emptyList())
            renderState.value = LCEState.Content(InventoryControlState.WarehouseLoaded(response))
        })
    }


    fun requestCategoryLista(warehouseCode: String? = null) {
        doAsynTask({
//            val listCategory = stockVisionRepository.categoryDao.getCategoriesByWarehouseOrAll(warehouseCode)
            val listCategory = stockVisionRepository.categoryDao.getCategoriesByWarehouseOrAll(warehouseCode)
            listCategory.map { category ->
                CategoryDTO(
                    codeCategory = category.categoryCode,
                    categoryName = category.categoryName
                )
            }
        }, {
            val response =
                ResponseGenericDTO(content = it, isValid = true, exceptions = emptyList())
            renderState.value =
                LCEState.Content(InventoryControlState.CategoriesLoaded(response))
        })
    }




    override val renderState: MutableLiveData<LCEState<InventoryControlState>>
        get() = getLiveData()
}
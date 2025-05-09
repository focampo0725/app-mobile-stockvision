package com.upc.stockvision.presentation.ui.reserve_space

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.upc.stockvision.data.repository.StockVisionRepository
import com.upc.stockvision.domain.dto.*
import com.upc.stockvision.domain.entities.Products
import com.upc.stockvision.domain.entities.ReserveArea
import com.upc.stockvision.infrastructure.extensions.LCEState
import com.upc.stockvision.infrastructure.extensions.doAsynTask
import com.upc.stockvision.infrastructure.extensions.doAsync
import com.upc.stockvision.infrastructure.extensions.logi
import com.upc.stockvision.presentation.BaseViewModel
import com.upc.stockvision.presentation.IViewModel
import com.upc.stockvision.presentation.ui.product_registration.ProductRegistrationState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

sealed class ReserveSpaceState{
    class CategoriesLoaded(val categoriesList: ResponseGenericDTO<CategoryDTO>) :
        ReserveSpaceState()

    class ProductLoaded(val productList: ResponseGenericDTO<ProductDTO>) :
        ReserveSpaceState()
    class WarehouseLoaded(val warehouseList: ResponseGenericDTO<WarehouseDTO>) :
        ReserveSpaceState()

    class AreaWarehouseLoaded(val areaWarehouseList: ResponseGenericDTO<AreaWarehouseDTO>) :
        ReserveSpaceState()

    class SuccessReserveRegister(val message: String) :
        ReserveSpaceState()

}

@HiltViewModel
class ReserveSpaceViewModel @Inject constructor(val stockVisionRepository : StockVisionRepository): BaseViewModel<LCEState<ReserveSpaceState>, ReserveSpaceState>(),
    IViewModel<ReserveSpaceState> {
    @SuppressLint("StaticFieldLeak")
    @Inject
    @ApplicationContext
    lateinit var context: Context
    fun requestCategoryList() {
        doAsynTask({
            val listCategory = stockVisionRepository.categoryDao.getAll()
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
                LCEState.Content(ReserveSpaceState.CategoriesLoaded(response))
        })
    }

    fun requestProductList(category: String){
        doAsynTask({
            val prodcutList = stockVisionRepository.productsDao.getProductsByCategory(category)
            prodcutList.map { product ->
                ProductDTO(
                    productName= product.productName,
                    categoryName = product.categoryName,
                    quantity = product.quantity,
                    supplierName = product.supplierName,
                    warehouse = product.warehouse,
                    areaWarehouse = product.areaWarehouse,
                    photo = product.photo,
                )
            }
        }, {
            val response =
                ResponseGenericDTO(content = it, isValid = true, exceptions = emptyList())
            renderState.value =
                LCEState.Content(ReserveSpaceState.ProductLoaded(response))
        })
    }


    fun requestWarehouse() {
        doAsynTask({
            val listCategory = stockVisionRepository.warehouseDao.getAll()
            listCategory.map { warehouse ->
                WarehouseDTO(
                    codeWarehouse = warehouse.warehouseCode,
                    warehouseName = warehouse.warehouseName
                )
            }
        }, {
            val response =
                ResponseGenericDTO(content = it, isValid = true, exceptions = emptyList())
            renderState.value = LCEState.Content(ReserveSpaceState.WarehouseLoaded(response))
        })
    }
    fun requestAreaWarehouse(warehose: String) {
        doAsynTask({
            val areaWarehouse = stockVisionRepository.warehouseDao.getWarehouseCodeByName(warehose)

            val listCategory = stockVisionRepository.areaWarehouseDao.getAll(areaWarehouse)
            listCategory.map { areawarehouse ->
                AreaWarehouseDTO(
                    codeAreaWarehouse = areawarehouse.areaWarehouseCode,
                    areaWarehouseName = areawarehouse.areaWarehouseName
                )
            }
        }, {
            val response =
                ResponseGenericDTO(content = it, isValid = true, exceptions = emptyList())
            renderState.value = LCEState.Content(ReserveSpaceState.AreaWarehouseLoaded(response))
        })
    }

    fun registerReserve(categoryName : String,productName : String,quantity : Int, warehouseName : String , areaWarehouseName : String , durationDays : Int , createAt : Date){
        val reserve = ReserveArea(
            categoryName = categoryName,
            productName =  productName,
            quantity = quantity,
            warehouseName = warehouseName,
            areaWarehouseName = areaWarehouseName,
            createAt = createAt,
            durationDays = durationDays)
        doAsync{
            try {
                stockVisionRepository.reserveAreaDao.insert(reserve)
                renderState.postValue(LCEState.Content(ReserveSpaceState.SuccessReserveRegister("Reserva registrada")))
            } catch (e: Exception) {
                context.logi("[EroorRegistro] -> $e")
            }
        }
    }

    override val renderState: MutableLiveData<LCEState<ReserveSpaceState>>
        get() = getLiveData()
}
package com.upc.stockvision.presentation.ui.detail_product

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import androidx.lifecycle.MutableLiveData
import com.upc.stockvision.data.repository.StockVisionRepository
import com.upc.stockvision.domain.dto.*
import com.upc.stockvision.domain.entities.ProductMovement
import com.upc.stockvision.domain.entities.ProductStock
import com.upc.stockvision.infrastructure.extensions.LCEState
import com.upc.stockvision.infrastructure.extensions.doAsynTask
import com.upc.stockvision.infrastructure.extensions.doAsync
import com.upc.stockvision.infrastructure.extensions.logi
import com.upc.stockvision.presentation.BaseViewModel
import com.upc.stockvision.presentation.IViewModel
import com.upc.stockvision.presentation.ui.create_product_movement.CreateProductMovementState
import com.upc.stockvision.presentation.ui.product_registration.ProductRegistrationState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

sealed class DetailProductState{
    class CategoriesLoadedOnUpdate (val categoryOnLoadedList : ResponseGenericDTO<CategoryDTO>) : DetailProductState()
    class WarehouseLoadedOnUpdate(val warehouseOnUpdateList: ResponseGenericDTO<WarehouseDTO>) : DetailProductState()
    class AreaWarehouseLoadedOnUpdate(val areaWarehouseOnUpdateList: ResponseGenericDTO<AreaWarehouseDTO>) : DetailProductState()
    class SuccessProductUpdate(val message: String) : DetailProductState()

}

@HiltViewModel
class DetailProductViewModel @Inject constructor(val stockVisionRepository: StockVisionRepository): BaseViewModel<LCEState<DetailProductState>, DetailProductState>(),
    IViewModel<DetailProductState> {

    @SuppressLint("StaticFieldLeak")
    @Inject
    @ApplicationContext
    lateinit var context: Context
    fun requestCategoryListOnUpdate() {
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
                LCEState.Content(DetailProductState.CategoriesLoadedOnUpdate(response))
        })
    }

    fun requestWarehouseOnUpdate(categoryCode: String) {
        doAsynTask({
            val listWarehouse = stockVisionRepository.warehouseDao.getWarehousesByCategory(categoryCode)
            listWarehouse.map { warehouse ->
                WarehouseDTO(
                    codeWarehouse = warehouse.warehouseCode,
                    warehouseName = warehouse.warehouseName
                )
            }
        }, {
            val response = ResponseGenericDTO(content = it, isValid = true, exceptions = emptyList())
            renderState.value = LCEState.Content(DetailProductState.WarehouseLoadedOnUpdate(response))
        })
    }
    fun requestAreaWarehouseOnUpdate(warehouseCode: String, categoryCode: String) {
        doAsynTask({
            val areas = stockVisionRepository.areaWarehouseDao
                .getAreasByWarehouseAndCategory(warehouseCode, categoryCode)

            areas.map { areawarehouse ->
                AreaWarehouseDTO(
                    codeAreaWarehouse = areawarehouse.areaWarehouseCode,
                    areaWarehouseName = areawarehouse.areaWarehouseName
                )
            }
        }, {
            val response = ResponseGenericDTO(content = it, isValid = true, exceptions = emptyList())
            renderState.value = LCEState.Content(DetailProductState.AreaWarehouseLoadedOnUpdate(response))
        })
    }


    fun updateByNameOrStock(procuctCode : String,areaWarehouseCode : String,productName : String?, stock : Int?){
        doAsync{

            try {
                stockVisionRepository.productsDao.updateProductData(productCode = procuctCode, productName = productName)
                stockVisionRepository.productStockDao.updateStockData(productCode = procuctCode, areaId = areaWarehouseCode,stock)
                renderState.postValue(LCEState.Content(DetailProductState.SuccessProductUpdate("Producto actualizado correctamente")))
            } catch (e: Exception) {
                context.logi("[ErrorUpdateProduct] -> $e")

            }
        }

    }

    fun updateCategoryAndRelocateStock(
        productCode: String,
        categoryCode: String,
        oldAreaId: String,
        newAreaId: String,
        quantity: Int,
        typeMovement: String
    ) {
        doAsync {
            try {
                // 1. Eliminar el stock anterior
                stockVisionRepository.productStockDao.deleteByProductAndArea(productCode, oldAreaId)

                val productStock = ProductStock(productCode,newAreaId,quantity)
                stockVisionRepository.productStockDao.insert(productStock)
                stockVisionRepository.productsDao.updateCategory(productCode, categoryCode)


                val movement = ProductMovement(
                    productCode = productCode,
                    initialAreaId = oldAreaId,
                    amountInitial = quantity,
                    amountFinalInitialArea = 0,
                    finalAreaId = newAreaId,
                    amountInitialFinalArea = 0,
                    amountMoved = quantity,
                    typeMovement = typeMovement

                )
                stockVisionRepository.productMovementDao.insert(movement)



            } catch (e: Exception) {
                context.logi("[ErrorReubicacion] -> $e")

            }
        }
    }





    fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }


    override val renderState: MutableLiveData<LCEState<DetailProductState>>
        get() = getLiveData()
}
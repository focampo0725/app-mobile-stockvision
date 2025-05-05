package com.upc.stockvision.presentation.ui.detail_product

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import androidx.lifecycle.MutableLiveData
import com.upc.stockvision.data.repository.StockVisionRepository
import com.upc.stockvision.domain.dto.*
import com.upc.stockvision.infrastructure.extensions.LCEState
import com.upc.stockvision.infrastructure.extensions.doAsynTask
import com.upc.stockvision.infrastructure.extensions.doAsync
import com.upc.stockvision.infrastructure.extensions.logi
import com.upc.stockvision.presentation.BaseViewModel
import com.upc.stockvision.presentation.IViewModel
import com.upc.stockvision.presentation.ui.product_registration.ProductRegistrationState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

sealed class DetailProductState{
    class CategoriesLoadedOnUpdate (val categoryOnLoadedList : ResponseGenericDTO<CategoryDTO>) : DetailProductState()
    class SupplierLoadedOnUpdate(val supplierOnUpdateList: ResponseGenericDTO<SupplierDTO>) : DetailProductState()
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
    fun requestSupplierOnUpdate() {
        doAsynTask({
            val listCategory = stockVisionRepository.supplierDao.getAll()
            listCategory.map { supplier ->
                SupplierDTO(
                    codeSupplier = supplier.supplierCode,
                    supplierName = supplier.supplierName
                )
            }
        }, {
            val response =
                ResponseGenericDTO(content = it, isValid = true, exceptions = emptyList())
            renderState.value = LCEState.Content(DetailProductState.SupplierLoadedOnUpdate(response))
        })
    }
    fun requestWarehouseOnUpdate() {
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
            renderState.value = LCEState.Content(DetailProductState.WarehouseLoadedOnUpdate(response))
        })
    }
    fun requestAreaWarehouseOnUpdate(warehose: String) {
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
            renderState.value = LCEState.Content(DetailProductState.AreaWarehouseLoadedOnUpdate(response))
        })
    }

    fun updateProduct(
        id: Int,
        productName: String? = null,
        categoryName: String? = null,
        quantity: Int? = null,
        supplierName: String? = null,
        warehouse: String? = null,
        areaWarehouse: String? = null,
        photo: String? = null
    ) {
        doAsync {
            try {
                stockVisionRepository.productsDao.updateProduct(id, productName, categoryName, quantity, supplierName, warehouse, areaWarehouse, photo)
                renderState.postValue(LCEState.Content(DetailProductState.SuccessProductUpdate("Producto Actualizado Correctamente")))
            } catch (e: Exception) {
                context.logi("[EroorRegistro] -> $e")
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
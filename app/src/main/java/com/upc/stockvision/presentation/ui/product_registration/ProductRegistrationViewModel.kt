package com.upc.stockvision.presentation.ui.product_registration

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import androidx.lifecycle.MutableLiveData
import com.upc.stockvision.data.repository.StockVisionRepository
import com.upc.stockvision.domain.dto.*
import com.upc.stockvision.domain.entities.Products
import com.upc.stockvision.infrastructure.extensions.*
import com.upc.stockvision.presentation.BaseViewModel
import com.upc.stockvision.presentation.IViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

sealed class ProductRegistrationState {
    class CategoriesLoaded(val categoriesList: ResponseGenericDTO<CategoryDTO>) :
        ProductRegistrationState()

    class ProductsLoaded(val productList: ResponseGenericDTO<SupplierDTO>) :
        ProductRegistrationState()

    class WarehouseLoaded(val warehouseList: ResponseGenericDTO<WarehouseDTO>) :
        ProductRegistrationState()

    class AreaWarehouseLoaded(val areaWarehouseList: ResponseGenericDTO<AreaWarehouseDTO>) :
        ProductRegistrationState()

    class SuccessProductRegister(val message: String) :
        ProductRegistrationState()

}

@HiltViewModel
class ProductRegistrationViewModel @Inject constructor(val stockVisionRepository: StockVisionRepository) :
    BaseViewModel<LCEState<ProductRegistrationState>, ProductRegistrationState>(),
    IViewModel<ProductRegistrationState> {

    @SuppressLint("StaticFieldLeak")
    @Inject
    @ApplicationContext
    lateinit var context: Context

//    fun requestCategoryList(){
//        super.sendValue(LCEState.loading(true))
//        stockVisionRepository.managementApi.getCategoryList().applySchedulers().subscribeApp(
//            onSuccess = {
//                super.sendValue(LCEState.loading(false))
//                renderState.value = LCEState.Content(ProductRegistrationState.CategoriesLoaded(it))
//            }, onError = {
//                super.sendValue(LCEState.loading(false))
//                context.showCustomToast("Error captred : $it",SelectedIcon.ERROR)
//
//            }
//        )
//
//    }

    fun requestCategoryLista() {
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
                LCEState.Content(ProductRegistrationState.CategoriesLoaded(response))
        })
    }
    fun requestSupplier() {
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
            renderState.value = LCEState.Content(ProductRegistrationState.ProductsLoaded(response))
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
            renderState.value = LCEState.Content(ProductRegistrationState.WarehouseLoaded(response))
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
            renderState.value = LCEState.Content(ProductRegistrationState.AreaWarehouseLoaded(response))
        })
    }

    fun registerProduct(productName: String, categoryName: String, quanty: Int, supplierName: String, warehouse: String, areaWarehouse: String, photo: String) {
        val product = Products(
            productName= productName,
            categoryName = categoryName,
            quantity = quanty,
            supplierName =  supplierName,
            warehouse =  warehouse,
            areaWarehouse =  areaWarehouse,
            photo =  photo
        )
        doAsync {
            try {
                stockVisionRepository.productsDao.insert(product)
                renderState.postValue(LCEState.Content(ProductRegistrationState.SuccessProductRegister("Producto registrado")))
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

    override val renderState: MutableLiveData<LCEState<ProductRegistrationState>>
        get() = getLiveData()
}
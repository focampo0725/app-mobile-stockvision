package com.upc.stockvision.presentation.ui.product_registration

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import androidx.lifecycle.MutableLiveData
import com.upc.stockvision.data.repository.StockVisionRepository
import com.upc.stockvision.data.room.dao.ProductDao
import com.upc.stockvision.domain.dto.*
import com.upc.stockvision.domain.entities.Product
import com.upc.stockvision.domain.entities.ProductStock
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

    fun requestWarehouse(categoryCode: String) {
        doAsynTask({
            val listWarehouse = stockVisionRepository.warehouseDao.getWarehousesByCategory(categoryCode)
            listWarehouse.map { warehouse ->
                WarehouseDTO(
                    codeWarehouse = warehouse.warehouseCode,
                    warehouseName = warehouse.warehouseName
                )
            }
        }, {
            val response =
                ResponseGenericDTO(content = it, isValid = true, exceptions = emptyList())
            renderState.value =
                LCEState.Content(ProductRegistrationState.WarehouseLoaded(response))
        })
    }


    fun requestAreaWarehouse(warehouseCode: String, categoryCode: String) {
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
            val response =
                ResponseGenericDTO(content = it, isValid = true, exceptions = emptyList())
            renderState.value =
                LCEState.Content(ProductRegistrationState.AreaWarehouseLoaded(response))
        })
    }

    fun generateUniqueProductCode(productsDao: ProductDao): String {
        val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        var code: String
        do {
            val randomPart = (1..3)
                .map { characters.random() }
                .joinToString("")
            code = "PRD-$randomPart"
        } while (productsDao.countProductCode(code) > 0)  // bloqueante: espera el resultado
        return code
    }

    fun registerProduct(
        productName: String,
        categoryCode: String,
        quantity: Int,
        supplierCode: String,
        areaWarehouseCode: String,
        photo: String
    ) {
        doAsync {
            try {
                // 1. Generar un código único para el producto
                val productCode = generateUniqueProductCode(stockVisionRepository.productsDao) // Puedes usar UUID o un prefijo como PRD-XYZ

                // 2. Crear el producto
                val product = Product(
                    productCode = productCode,
                    productName = productName,
                    categoryCode = categoryCode,
                    supplierCode = supplierCode,
                    photo = photo
                )

                // 3. Insertar el producto (no necesitas el ID)
                stockVisionRepository.productsDao.insert(product)

                // 4. Insertar el stock relacionado a ese producto
                val productStock = ProductStock(
                    productCode = productCode,
                    areaCode = areaWarehouseCode,
                    stock = quantity
                )
                stockVisionRepository.productStockDao.insert(productStock)

                // 5. Notificar éxito
                renderState.postValue(
                    LCEState.Content(
                        ProductRegistrationState.SuccessProductRegister("Producto registrado correctamente")
                    )
                )
            } catch (e: Exception) {
                context.logi("[ErrorRegistroProducto] -> $e")
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
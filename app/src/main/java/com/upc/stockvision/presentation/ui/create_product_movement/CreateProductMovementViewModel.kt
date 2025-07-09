package com.upc.stockvision.presentation.ui.create_product_movement


import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.upc.stockvision.data.repository.StockVisionRepository
import com.upc.stockvision.domain.dto.*
import com.upc.stockvision.domain.entities.ProductMovement
import com.upc.stockvision.domain.entities.ProductStock
import com.upc.stockvision.infrastructure.extensions.LCEState
import com.upc.stockvision.infrastructure.extensions.doAsynTask
import com.upc.stockvision.presentation.BaseViewModel
import com.upc.stockvision.presentation.IViewModel
import com.upc.stockvision.presentation.ui.product_registration.ProductRegistrationState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

sealed class CreateProductMovementState{
    class CategoriesLoaded(val categoriesList: ResponseGenericDTO<CategoryDTO>) :
        CreateProductMovementState()

    class ProductLoaded(val productList: ResponseGenericDTO<ProductOnDetailDTO>) :
        CreateProductMovementState()
    class WarehouseLoaded(val warehouseList: ResponseGenericDTO<WarehouseDTO>) :
        CreateProductMovementState()

    class AreaWarehouseLoaded(val areaWarehouseList: ResponseGenericDTO<AreaWarehouseDTO>) :
        CreateProductMovementState()

    class TypeProductMovementLoaded(val typeProductMovementList: ResponseGenericDTO<TypeMovementDTO>) :
        CreateProductMovementState()
    class CreateProductMovementDetails(val messsage : String) : CreateProductMovementState()

}

@HiltViewModel
class CreateProductMovementViewModel @Inject constructor(val stockVisionRepository : StockVisionRepository): BaseViewModel<LCEState<CreateProductMovementState>, CreateProductMovementState>(),
    IViewModel<CreateProductMovementState> {
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
                LCEState.Content(CreateProductMovementState.CategoriesLoaded(response))
        })
    }

    fun requestProductList(categoryCode: String) {
        doAsynTask({
            stockVisionRepository.productsDao.getProductsByCategory(categoryCode)
        }, {
            val response = ResponseGenericDTO(content = it, isValid = true, exceptions = emptyList())
            renderState.value = LCEState.Content(CreateProductMovementState.ProductLoaded(response))
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
            renderState.value = LCEState.Content(CreateProductMovementState.WarehouseLoaded(response))
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
            renderState.value = LCEState.Content(CreateProductMovementState.AreaWarehouseLoaded(response))
        })
    }


    fun reqeustTypeMovement(){
        val typeMovement = listOf(
            TypeMovementDTO(1,"Traslado-Almacen lleno"),
            TypeMovementDTO(2,"Error de Entrada"),
            TypeMovementDTO(3,"Traslado-Salida")
        )
        val response = ResponseGenericDTO(content = typeMovement, isValid = true, exceptions = emptyList())
        renderState.value = LCEState.Content(CreateProductMovementState.TypeProductMovementLoaded(response))
    }

    fun createMovement(
        productId: String,
        initialAreaId: String,
        finalAreaId: String,
        amountMoved: Int,
        typeMovement: String
    ) {

        val movementDao = stockVisionRepository.productMovementDao


        val stockOrigin = stockVisionRepository.productStockDao.getByProductAndArea(productId, initialAreaId)
            ?: throw IllegalStateException("No hay stock en el área de origen.")


        val stockInicialOrigen = stockOrigin.stock
        require(stockOrigin.stock >= amountMoved) {
            "No hay suficiente stock para mover. Disponible: ${stockOrigin.stock}, requerido: $amountMoved"
        }
        stockOrigin.stock -= amountMoved
        stockVisionRepository.productStockDao.update(stockOrigin)
        val stockFinalOrigen = stockOrigin.stock

        // Obtener stock en área destino
        val stockDestino = stockVisionRepository.productStockDao.getByProductAndArea(productId, finalAreaId)
        val stockInicialDestino = stockDestino?.stock ?: 0

        if (stockDestino != null) {
            stockDestino.stock += amountMoved
            stockVisionRepository.productStockDao.update(stockDestino)
        } else {
            val newProductStock = ProductStock(
                productCode = productId,
                areaCode = finalAreaId,
                stock = amountMoved
            )
            stockVisionRepository.productStockDao.insert(newProductStock)
        }

        val movement = ProductMovement(
            productCode = productId,
            initialAreaId = initialAreaId,
            amountInitial = stockInicialOrigen,
            amountFinalInitialArea = stockFinalOrigen,
            finalAreaId = finalAreaId,
            amountInitialFinalArea = stockInicialDestino,
            amountMoved = amountMoved,
            typeMovement = typeMovement
        )

        movementDao.insert(movement)

        renderState.postValue(
            LCEState.Content(
                CreateProductMovementState.CreateProductMovementDetails("Registro exitoso")
            )
        )
    }



    override val renderState: MutableLiveData<LCEState<CreateProductMovementState>>
        get() = getLiveData()
}
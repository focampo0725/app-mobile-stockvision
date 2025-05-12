package com.upc.stockvision.presentation.ui.create_product_movement

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.upc.stockvision.data.repository.StockVisionRepository
import com.upc.stockvision.domain.dto.*
import com.upc.stockvision.domain.entities.ProductMovement
import com.upc.stockvision.domain.entities.Products
import com.upc.stockvision.domain.entities.ReserveArea
import com.upc.stockvision.infrastructure.extensions.LCEState
import com.upc.stockvision.infrastructure.extensions.doAsynTask
import com.upc.stockvision.infrastructure.extensions.doAsync
import com.upc.stockvision.infrastructure.extensions.logi
import com.upc.stockvision.presentation.BaseViewModel
import com.upc.stockvision.presentation.IViewModel
import com.upc.stockvision.presentation.ui.inventory_control.InventoryControlState
import com.upc.stockvision.presentation.ui.product_registration.ProductRegistrationState
import com.upc.stockvision.presentation.ui.reserve_space.ReserveSpaceState
import com.upc.stockvision.presentation.ui.show_reservation.ShowReservationState
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

    fun requestProductList(category: String){
        doAsynTask({
            val prodcutList = stockVisionRepository.productsDao.getProductsByCategory(category)
            prodcutList.map { product ->
                ProductOnDetailDTO(
                    idProduct = product.id,
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
                LCEState.Content(CreateProductMovementState.ProductLoaded(response))
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
            renderState.value = LCEState.Content(CreateProductMovementState.WarehouseLoaded(response))
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
            renderState.value = LCEState.Content(CreateProductMovementState.AreaWarehouseLoaded(response))
        })
    }

    fun reqeustTypeMovement(){
        val typeMovement = listOf(
            TypeMovementDTO(1,"Traslado-Almacen lleno"),
            TypeMovementDTO(1,"Error de Entrada"),
            TypeMovementDTO(1,"Traslado-Salida")
        )
        val response = ResponseGenericDTO(content = typeMovement, isValid = true, exceptions = emptyList())
        renderState.value = LCEState.Content(CreateProductMovementState.TypeProductMovementLoaded(response))
    }

    fun createMovement( creationUser : String, productName : String, initialWarehouse: String, initialAreaWarehouse: String, finalWarehouse: String, finalAreaWarehouse: String, amountMoved : Int, typeMovement: String) {
        val productMovement = ProductMovement(
            creationUser = creationUser,
            productName = productName,
            initialWarehouse = initialWarehouse,
            initialAreaWarehouse = initialAreaWarehouse,
            finalWarehouse = finalWarehouse,
            finalAreaWarehouse = finalAreaWarehouse,
            amountMoved = amountMoved,
            typeMovement = typeMovement,
            movementDate = Date() )

        doAsync{
            try {
                stockVisionRepository.productMovementDao.insert(productMovement)
                renderState.postValue(LCEState.Content(CreateProductMovementState.CreateProductMovementDetails("Registro exitoso")))
            } catch (e: Exception) {
                context.logi("[EroorRegistro] -> $e")
            }
        }

    }

    fun updateProductMovement(idProduct : Int , productQuantity : Int ,warehouse: String, areaWarehouse: String,newProductQuantity : Int ){

        doAsynTask({
            val getProducts = stockVisionRepository.productsDao.getOnlyProduct(idProduct)
            getProducts
        },{
            val newProductForMovement = Products(
                productName= it.productName,
                categoryName = it.categoryName,
                quantity = newProductQuantity,
                supplierName =  it.supplierName,
                warehouse =  warehouse,
                areaWarehouse =  areaWarehouse,
                photo =  it.photo
            )

            doAsync{
                try {
                    stockVisionRepository.productsDao.updateQuantityForMovement(idProduct, productQuantity)
                    stockVisionRepository.productsDao.insert(newProductForMovement)
                } catch (e: Exception) {
                    context.logi("[EroorRegistro] -> $e")
                }
            }
        })


    }

    override val renderState: MutableLiveData<LCEState<CreateProductMovementState>>
        get() = getLiveData()
}
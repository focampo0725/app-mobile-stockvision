package com.upc.stockvision.presentation.ui.detail_reserve_space

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
import com.upc.stockvision.presentation.ui.product_registration.ProductRegistrationState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

sealed class DetailReserveState{


}

@HiltViewModel
class DetailReserveViewModel @Inject constructor(val stockVisionRepository: StockVisionRepository): BaseViewModel<LCEState<DetailReserveState>, DetailReserveState>(),
    IViewModel<DetailReserveState> {

    @SuppressLint("StaticFieldLeak")
    @Inject
    @ApplicationContext
    lateinit var context: Context


    fun MakeReservation(
        productId: String,
        destinationAreaId: String,
        amountArrived: Int,
        typeMovement: String
    ) {
        // Paso 1: Loguear los datos de entrada para trazabilidad
        context.logi("Registro de llegada -> Producto: $productId, Hacia: $destinationAreaId, Cantidad: $amountArrived, Tipo: $typeMovement")

        doAsync {
            val stockDao = stockVisionRepository.productStockDao
            val movementDao = stockVisionRepository.productMovementDao

            // Paso 2: Validar que la cantidad sea mayor a 0
            if (amountArrived <= 0) {
                context.logi("Cantidad inválida: $amountArrived")
                return@doAsync
            }

            // Paso 3: Verificar si ya existe stock del producto en el área de destino (lugar donde llega)
            val stockInDestination = stockDao.getByProductAndArea(productId, destinationAreaId)
            val initialStockDestination = stockInDestination?.stock ?: 0

            // Paso 4: Determinar el área de origen
            val sourceAreaId = if (stockInDestination != null) {
                // Si el producto ya está en el área de destino, asumimos que no hay cambio de área
                destinationAreaId
            } else {
                // Si no está en destino, buscamos en qué otra área está disponible
                val otherStockAreas = stockDao.getByProduct(productId)
                val sourceStock = otherStockAreas.firstOrNull { it.areaCode != destinationAreaId && it.stock > 0 }

                sourceStock?.areaCode ?: "UNKNOWN" // puede ser útil para controlar errores o trazabilidad
            }

            // Paso 5: Actualizar o insertar el stock en destino
            if (stockInDestination != null) {
                // Ya hay stock en el área de destino, solo actualizamos
                stockInDestination.stock += amountArrived
                stockDao.update(stockInDestination)
            } else {
                // No había stock en destino, insertamos un nuevo registro
                val newStock = ProductStock(
                    productCode = productId,
                    areaCode = destinationAreaId,
                    stock = amountArrived
                )
                stockDao.insert(newStock)
            }

            val finalStockDestination = initialStockDestination + amountArrived

            // Paso 6: Registrar el movimiento en la base de datos
            val movement = ProductMovement(
                productCode = productId,
                initialAreaId = sourceAreaId,               // Donde estaba originalmente (si ya estaba en destino, será el mismo)
                amountInitial = initialStockDestination,    // Stock en destino antes de llegar
                amountFinalInitialArea = finalStockDestination, // Stock en destino después de llegar
                finalAreaId = destinationAreaId,            // Donde llegó
                amountInitialFinalArea = initialStockDestination,
                amountMoved = amountArrived,
                typeMovement = typeMovement
            )

            movementDao.insert(movement)

            // Paso 7: Notificar éxito
//        renderState.postValue(LCEState.Content(CreateProductMovementState.CreateProductMovementDetails("Llegada registrada con éxito")))
        }
    }


    fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }


    override val renderState: MutableLiveData<LCEState<DetailReserveState>>
        get() = getLiveData()
}
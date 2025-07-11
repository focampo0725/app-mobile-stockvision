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
import com.upc.stockvision.domain.entities.ReserveArea.Companion.CANCEL_RESERVE
import com.upc.stockvision.domain.entities.ReserveArea.Companion.CONFIRM_RESERVE
import com.upc.stockvision.infrastructure.extensions.LCEState
import com.upc.stockvision.infrastructure.extensions.doAsynTask
import com.upc.stockvision.infrastructure.extensions.doAsync
import com.upc.stockvision.infrastructure.extensions.logi
import com.upc.stockvision.presentation.BaseViewModel
import com.upc.stockvision.presentation.IViewModel
import com.upc.stockvision.presentation.ui.inventory_control.InventoryControlState
import com.upc.stockvision.presentation.ui.product_registration.ProductRegistrationState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

sealed class DetailReserveState {
    class ReserveState(val messageConfirm : String) : DetailReserveState()

}

@HiltViewModel
class DetailReserveViewModel @Inject constructor(val stockVisionRepository: StockVisionRepository): BaseViewModel<LCEState<DetailReserveState>, DetailReserveState>(),
    IViewModel<DetailReserveState> {

    @SuppressLint("StaticFieldLeak")
    @Inject
    @ApplicationContext
    lateinit var context: Context


    fun cancelReservation(reserveId: Int){
        doAsync{
            stockVisionRepository.reserveAreaDao.updateState(reserveId, CANCEL_RESERVE)
            renderState.postValue(LCEState.Content(DetailReserveState.ReserveState("Reserva Cancelada")))
        }
    }
    fun MakeReservation(
        reserveId : Int,
        productId: String,
        destinationAreaId: String,
        amountArrived: Int,
        typeMovement: String
    ) {
        // Paso 1: Log para trazabilidad
        context.logi("Registro de llegada -> Producto: $productId, Hacia: $destinationAreaId, Cantidad: $amountArrived, Tipo: $typeMovement")

        doAsync {
            val stockDao = stockVisionRepository.productStockDao
            val movementDao = stockVisionRepository.productMovementDao

            // Paso 2: Validar cantidad positiva
            if (amountArrived <= 0) {
                context.logi("Cantidad inválida: $amountArrived")
                return@doAsync
            }

            // Paso 3: Obtener stock en el área de destino
            val stockInDestination = stockDao.getByProductAndArea(productId, destinationAreaId)
            val initialStockDestination = stockInDestination?.stock ?: 0

            // Paso 4: Determinar el área de origen
            val sourceAreaId = if (stockInDestination != null) {
                // Si ya hay stock en destino, se considera que el producto ya estaba ahí
                destinationAreaId
            } else {
                // Si no hay stock en destino, buscar la primera otra área donde haya stock del producto
                val existingStockInOtherArea = stockDao.getFirstByProduct(productId)
                existingStockInOtherArea?.areaCode ?: destinationAreaId // fallback por seguridad
            }

            // Paso 5: Insertar o actualizar el stock en destino
            val finalStockDestination = initialStockDestination + amountArrived

            if (stockInDestination != null) {
                stockInDestination.stock = finalStockDestination
                stockDao.update(stockInDestination)
            } else {
                val newStock = ProductStock(
                    productCode = productId,
                    areaCode = destinationAreaId,
                    stock = amountArrived
                )
                stockDao.insert(newStock)
            }

            // Paso 6: Registrar el movimiento
            val movement = ProductMovement(
                productCode = productId,
                initialAreaId = sourceAreaId,
                amountInitial = initialStockDestination,
                amountFinalInitialArea = finalStockDestination,
                finalAreaId = destinationAreaId,
                amountInitialFinalArea = initialStockDestination,
                amountMoved = amountArrived,
                typeMovement = typeMovement
            )

            movementDao.insert(movement)
            stockVisionRepository.reserveAreaDao.updateState(reserveId,CONFIRM_RESERVE)

            // Paso 7: (opcional) Notificación o estado
             renderState.postValue(LCEState.Content(DetailReserveState.ReserveState("Ingreso Confirmado")))
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
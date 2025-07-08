package com.upc.stockvision.infrastructure.service

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.upc.stockvision.R
import com.upc.stockvision.StockVisionApp
import com.upc.stockvision.data.repository.StockVisionRepository
import com.upc.stockvision.databinding.CustomAlertIncomingProductBinding
import com.upc.stockvision.domain.entities.Notifications
import com.upc.stockvision.domain.entities.ReserveArea
import com.upc.stockvision.infrastructure.AppState
import com.upc.stockvision.infrastructure.extensions.doAsynTask
import com.upc.stockvision.infrastructure.extensions.doAsync
import com.upc.stockvision.presentation.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FcmService : FirebaseMessagingService() {

    @Inject
    lateinit var stockVisionRepository: StockVisionRepository
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        showNotification(message)
    }

    @SuppressLint("RemoteViewLayout")
    private fun showNotification(message: RemoteMessage) {
        val messageContent = message.notification?.body
        val messageParts = messageContent?.split("|")
        val fragmentToOpen = messageParts?.get(0)
        val intent = Intent(this, HomeActivity::class.java).apply {
            putExtra("fragment_to_show", fragmentToOpen)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notificationManager = getSystemService(NotificationManager::class.java)

        val remoteView: RemoteViews = when (fragmentToOpen) {
            "0" -> RemoteViews(packageName, R.layout.custom_alert_incoming_product).apply {
                val productId = messageParts?.get(1)?.toIntOrNull() ?: return@apply
                val arrivalDate = messageParts?.get(2) ?: return@apply
                val quantity = messageParts?.get(3)?.toIntOrNull() ?: return@apply

                doAsynTask({
                    val product = stockVisionRepository.productsDao.getById(productId)
                    val area = stockVisionRepository.areaWarehouseDao.getByCode(product.categoryCode)
                    val warehouse = area?.let {
                        stockVisionRepository.warehouseDao.getByCode(it.warehouseReference)
                    }
                    Triple(product, area, warehouse)
                }, { (product, area, warehouse) ->
                    // Mostrar en notificación
                    setTextViewText(R.id.tvIncomingProduct, product.productName)
                    setTextViewText(R.id.tvArea, area?.areaWarehouseName ?: "Área desconocida")
                    setTextViewText(R.id.tvAlmacen, warehouse?.warehouseName ?: "Almacén desconocido")
                    setTextViewText(R.id.tvIncomingQuantity, quantity.toString())
                    setTextViewText(R.id.tvIncomingDate, arrivalDate)

                    // Guardar notificación
                    doAsync {
                        stockVisionRepository.notificationsDao.insert(
                            Notifications(
                                typeNotification = 0,
                                productId = product.id,
                                areaId = area?.areaWarehouseCode ?: "",
                                quantity = quantity
                            )
                        )
                    }
                })
            }

            "1" -> RemoteViews(packageName, R.layout.custom_alert_reserve_area).apply {
                val areaCode = messageParts?.get(1) ?: return@apply
                val productId = messageParts?.get(2)?.toIntOrNull() ?: return@apply
                val quantity = messageParts?.get(3)?.toIntOrNull() ?: return@apply
                val arrivalDate = messageParts?.get(4) ?: return@apply

                // Consultar nombres desde la base de datos
                doAsynTask({
                    val area = stockVisionRepository.areaWarehouseDao.getByCode(areaCode)
                    val warehouse = area?.let {
                        stockVisionRepository.warehouseDao.getByCode(it.warehouseReference)
                    }
                    Pair(area?.areaWarehouseName ?: "Área desconocida", warehouse?.warehouseName ?: "Almacén desconocido")
                }, { (areaName, warehouseName) ->
                    // Pintar la notificación con nombres
                    setTextViewText(R.id.tvArea, areaName)
                    setTextViewText(R.id.tvAlmacen, warehouseName)

                    // Insertar la reserva y notificación
                    doAsync {
                        val reserve = ReserveArea(
                            productId = productId,
                            areaId = areaCode,
                            quantity = quantity,
                            arrivalDate = arrivalDate
                        )
                        stockVisionRepository.reserveAreaDao.insert(reserve)

                        val notification = Notifications(
                            typeNotification = 1, productId = productId, areaId = areaCode, quantity = quantity
                        )
                        stockVisionRepository.notificationsDao.insert(notification)
                    }
                })
            }


            "2" -> RemoteViews(packageName, R.layout.custom_alert_low_stock_product).apply {
                setTextViewText(R.id.tvLowStockProduct, messageParts?.get(1) ?: "")
                doAsynTask({
                    stockVisionRepository.productsDao.getProductByName(messageParts?.get(1)!!)
                },{
                    doAsync{
//                        stockVisionRepository.notificationsDao.insert(Notifications(2,"",it.productName,it.warehouse,it.areaWarehouse,3))
                    }

                })


            }

            else -> RemoteViews(packageName, R.layout.custom_alert_low_stock_product).apply {
                setTextViewText(R.id.tvLowStockProduct, "Mensaje no reconocido")
            }
        }




        val notification = NotificationCompat.Builder(this, StockVisionApp.NOTIFICATION_CHANNEL_ID)
            .setContentTitle(message.notification?.title)
////            .setContentText(message.notification?.body)
            .setSmallIcon(R.drawable.ic_bell)
            .setCustomBigContentView(remoteView)  // Usamos el RemoteViews para la vista personalizada
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
        notificationManager.notify(1, notification)
    }

}

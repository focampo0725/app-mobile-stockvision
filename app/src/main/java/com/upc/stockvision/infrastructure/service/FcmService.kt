package com.upc.stockvision.infrastructure.service

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
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
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class FcmService : FirebaseMessagingService() {

    @Inject
    lateinit var stockVisionRepository: StockVisionRepository

    @ApplicationContext
    lateinit var context: Context

    var areaT =""
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        showNotification(message)
    }

    @SuppressLint("RemoteViewLayout")
    private fun showNotification(message: RemoteMessage) {
        val messageContent = message.notification?.body
        val messageParts = messageContent?.split("|")
        val fragmentToOpen = messageParts?.getOrNull(0) ?: return

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

        when (fragmentToOpen) {
            "0" -> {
                val productId = messageParts.getOrNull(1) ?: return
                val arrivalDate = messageParts.getOrNull(2) ?: return
                val quantity = messageParts.getOrNull(3)?.toIntOrNull() ?: return

                doAsynTask({
                    val product = stockVisionRepository.productsDao.getByProductCode(productId.toString())
                    val area1 = stockVisionRepository.productStockDao.getFirstByProduct(product.productCode)
                    val area = stockVisionRepository.areaWarehouseDao.getByCode(area1!!.areaCode)
                    val warehouse = area?.let {
                        stockVisionRepository.warehouseDao.getByCode(it.warehouseReference)
                    }
                    Triple(product, area, warehouse)
                }, { (product, area, warehouse) ->
                    val remoteView = RemoteViews(packageName, R.layout.custom_alert_incoming_product).apply {
                        setTextViewText(R.id.tvIncomingProduct, product.productName)
                        setTextViewText(R.id.tvIncomingQuantity, quantity.toString())
                        setTextViewText(R.id.tvIncomingDate, arrivalDate)
                    }

                    val notification = NotificationCompat.Builder(this, StockVisionApp.NOTIFICATION_CHANNEL_ID)
                        .setContentTitle(message.notification?.title)
                        .setSmallIcon(R.drawable.ic_bell)
                        .setCustomBigContentView(remoteView)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .build()

                    notificationManager.notify(1, notification)

                    doAsync {
                        stockVisionRepository.notificationsDao.insert(
                            Notifications(
                                typeNotification = 0,
                                productCode = product.productCode,
                                areaId = area?.areaWarehouseCode ?: "",
                                quantity = quantity
                            )
                        )
                    }
                })
            }

            "1" -> {
                val areaCode = messageParts.getOrNull(1) ?: return
                val productId = messageParts.getOrNull(2) ?: return
                val quantity = messageParts.getOrNull(3)?.toIntOrNull() ?: return
                val arrivalDate = messageParts.getOrNull(4) ?: return

                doAsynTask({
                    val area = stockVisionRepository.areaWarehouseDao.getByCode(areaCode)
                    val warehouse = area?.let {
                        stockVisionRepository.warehouseDao.getByCode(it.warehouseReference)
                    }
                    Pair(area?.areaWarehouseName ?: "Área desconocida", warehouse?.warehouseName ?: "Almacén desconocido")
                }, { (areaName, warehouseName) ->
                    val remoteView = RemoteViews(packageName, R.layout.custom_alert_reserve_area).apply {
                        setTextViewText(R.id.tvArea, areaName)
                        setTextViewText(R.id.tvAlmacen, warehouseName)
                    }

                    val notification = NotificationCompat.Builder(this, StockVisionApp.NOTIFICATION_CHANNEL_ID)
                        .setContentTitle(message.notification?.title)
                        .setSmallIcon(R.drawable.ic_bell)
                        .setCustomBigContentView(remoteView)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .build()

                    notificationManager.notify(1, notification)

                    doAsync {
                        val reserve = ReserveArea(
                            productCode = productId,
                            areaId = areaCode,
                            quantity = quantity,
                            arrivalDate = arrivalDate
                        )
                        stockVisionRepository.reserveAreaDao.insert(reserve)

                        val notificationEntity = Notifications(
                            typeNotification = 1,
                            productCode = productId,
                            areaId = areaCode,
                            quantity = quantity
                        )
                        stockVisionRepository.notificationsDao.insert(notificationEntity)
                    }
                })
            }

            "2" -> {
                val productId = messageParts.getOrNull(1) ?: return
                val areaCode = messageParts.getOrNull(2) ?: return

                doAsynTask({
                    val stock = stockVisionRepository.productStockDao.getByProductAndArea(productId, areaCode)
                    stock
                }, { stock ->
                    val remoteView = RemoteViews(packageName, R.layout.custom_alert_low_stock_product).apply {
                        setTextViewText(R.id.tvLowStockProduct, productId)
                    }

                    val notification = NotificationCompat.Builder(this, StockVisionApp.NOTIFICATION_CHANNEL_ID)
                        .setContentTitle(message.notification?.title)
                        .setSmallIcon(R.drawable.ic_bell)
                        .setCustomBigContentView(remoteView)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .build()

                    notificationManager.notify(1, notification)

                    doAsync {
                        val notificationEntity = Notifications(
                            typeNotification = 2,
                            productCode = productId,
                            areaId = areaCode,
                            quantity = stock?.stock ?: 0
                        )
                        stockVisionRepository.notificationsDao.insert(notificationEntity)
                    }
                })
            }

            else -> {
                val remoteView = RemoteViews(packageName, R.layout.custom_alert_low_stock_product).apply {
                    setTextViewText(R.id.tvLowStockProduct, "Mensaje no reconocido")
                }

                val notification = NotificationCompat.Builder(this, StockVisionApp.NOTIFICATION_CHANNEL_ID)
                    .setContentTitle(message.notification?.title)
                    .setSmallIcon(R.drawable.ic_bell)
                    .setCustomBigContentView(remoteView)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .build()

                notificationManager.notify(1, notification)
            }
        }
    }


}

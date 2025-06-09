package com.upc.stockvision.presentation.ui.notifications

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.upc.stockvision.data.repository.StockVisionRepository
import com.upc.stockvision.domain.dto.*
import com.upc.stockvision.domain.entities.Products
import com.upc.stockvision.domain.entities.ReserveArea
import com.upc.stockvision.infrastructure.extensions.LCEState
import com.upc.stockvision.infrastructure.extensions.doAsynTask
import com.upc.stockvision.infrastructure.extensions.doAsync
import com.upc.stockvision.infrastructure.extensions.logi
import com.upc.stockvision.presentation.BaseViewModel
import com.upc.stockvision.presentation.IViewModel
import com.upc.stockvision.presentation.ui.product_registration.ProductRegistrationState
import com.upc.stockvision.presentation.ui.show_reservation.ShowReservationState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

sealed class NotificationsState{
    class NotificationLoaded(val notificationList : List<NotificationDTO>) : NotificationsState()

}

@HiltViewModel
class NotificationsViewModel @Inject constructor(val stockVisionRepository : StockVisionRepository): BaseViewModel<LCEState<NotificationsState>, NotificationsState>(),
    IViewModel<NotificationsState> {
    @SuppressLint("StaticFieldLeak")
    @Inject
    @ApplicationContext
    lateinit var context: Context

    fun requestReserveList() {
        doAsynTask({
            val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

            val listNotifications = stockVisionRepository.notificationsDao.getAllNotificatios()
            listNotifications.map { notification ->
                NotificationDTO(
                    typeNotification = notification.typeNotification,
                    title = notification.title,
                    productName = notification.productName,
                    quantity = notification.quantity,
                    warehouseName = notification.warehouseName,
                    warehouseArea = notification.warehouseArea,
                    date = formato.format(notification.createAT)
                )
            }
        }, {
            renderState.value = LCEState.Content(NotificationsState.NotificationLoaded(it))
        })
    }



    override val renderState: MutableLiveData<LCEState<NotificationsState>>
        get() = getLiveData()
}
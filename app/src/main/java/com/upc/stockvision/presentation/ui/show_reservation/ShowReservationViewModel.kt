package com.upc.stockvision.presentation.ui.show_reservation

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.upc.stockvision.data.repository.StockVisionRepository
import com.upc.stockvision.domain.dto.*
import com.upc.stockvision.domain.entities.Product
import com.upc.stockvision.domain.entities.ReserveArea
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
import java.util.*
import javax.inject.Inject

sealed class ShowReservationState{
    class ReserveLoaded(val reserveList : List<ReservationDetailDTO>) : ShowReservationState()


}

@HiltViewModel
class ShowReservationViewModel @Inject constructor(val stockVisionRepository : StockVisionRepository): BaseViewModel<LCEState<ShowReservationState>, ShowReservationState>(),
    IViewModel<ShowReservationState> {
    @SuppressLint("StaticFieldLeak")
    @Inject
    @ApplicationContext
    lateinit var context: Context

    fun requestReserveList() {
//        doAsynTask({
//            val listCategory = stockVisionRepository.reserveAreaDao.getAllReserve()
//            listCategory.map { reserve ->
//                ReserveAreaDTO(
//                    id = reserve.id,
//                    categoryName = reserve.categoryName,
//                    productName = reserve.productName,
//                    quantity = reserve.quantity,
//                    warehouseName = reserve.warehouseName,
//                    areaWarehouseName = reserve.areaWarehouseName,
//                    createAt = reserve.createAt.toString(),
//                    durationDays = reserve.durationDays
//                )
//            }
//        }, {
//            renderState.value = LCEState.Content(ShowReservationState.ReserveLoaded(it))
//        })
    }

    override val renderState: MutableLiveData<LCEState<ShowReservationState>>
        get() = getLiveData()
}
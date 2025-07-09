package com.upc.stockvision.presentation.ui.product_movement_record

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.upc.stockvision.data.repository.StockVisionRepository
import com.upc.stockvision.domain.dto.*
import com.upc.stockvision.infrastructure.extensions.LCEState
import com.upc.stockvision.infrastructure.extensions.doAsynTask
import com.upc.stockvision.presentation.BaseViewModel
import com.upc.stockvision.presentation.IViewModel
import com.upc.stockvision.presentation.ui.inventory_control.InventoryControlState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

sealed class ProductMovementRecordState{
    class ProductMovementLoaded(val productMovementList : List<ProductMovementDTO>) : ProductMovementRecordState()


}

@HiltViewModel
class ProductMovementRecordViewModel @Inject constructor(val stockVisionRepository : StockVisionRepository): BaseViewModel<LCEState<ProductMovementRecordState>, ProductMovementRecordState>(),
    IViewModel<ProductMovementRecordState> {
    @SuppressLint("StaticFieldLeak")
    @Inject
    @ApplicationContext
    lateinit var context: Context

    fun requestProductMovementList() {
        doAsynTask({
            stockVisionRepository.productMovementDao.getAllMovementsWithDetails()
        }, {
            renderState.value = LCEState.Content(ProductMovementRecordState.ProductMovementLoaded(it))
        })
    }

    override val renderState: MutableLiveData<LCEState<ProductMovementRecordState>>
        get() = getLiveData()
}
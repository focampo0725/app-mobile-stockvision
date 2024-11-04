package com.upc.stockvision.presentation.ui.inventory_control

import androidx.lifecycle.MutableLiveData
import com.upc.stockvision.infrastructure.extensions.LCEState
import com.upc.stockvision.presentation.BaseViewModel
import com.upc.stockvision.presentation.IViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed class InventoryControlState{

}

@HiltViewModel
class InventoryControlViewModel @Inject constructor(): BaseViewModel<LCEState<InventoryControlState>, InventoryControlState>(),
    IViewModel<InventoryControlState> {

    override val renderState: MutableLiveData<LCEState<InventoryControlState>>
        get() = getLiveData()
}
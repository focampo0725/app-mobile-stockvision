package com.upc.stockvision.presentation

import androidx.lifecycle.MutableLiveData
import com.upc.stockvision.infrastructure.extensions.LCEState

interface IViewModel<T> {
    val renderState : MutableLiveData<LCEState<T>>
}


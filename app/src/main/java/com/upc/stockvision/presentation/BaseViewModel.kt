package com.upc.stockvision.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.upc.stockvision.infrastructure.extensions.LCEState

open class BaseViewModel<T : LCEState<K>, K> : ViewModel() {
    private var mutableLiveData : MutableLiveData<Any>? = null

    fun <T>getLiveData() : MutableLiveData<T> {
        if(mutableLiveData == null)
            mutableLiveData = MutableLiveData()
        return mutableLiveData as MutableLiveData<T>
    }

    private fun<K> parseValueLCE(data : K): Any {
        if(data is LCEState.Loading<*> || data is LCEState.Error<*>)
            return data
        else(data is LCEState.Content<*>)
        return LCEState.content(data)
    }
    fun <K>sendValue(message : K){
        mutableLiveData?.value = parseValueLCE(message)
    }
    fun <K>postMessage(message : K){
        mutableLiveData?.postValue(parseValueLCE(message))
    }


}
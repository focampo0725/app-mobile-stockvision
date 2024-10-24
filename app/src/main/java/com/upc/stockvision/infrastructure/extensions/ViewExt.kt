package com.upc.stockvision.infrastructure.extensions

import android.content.Context
import android.icu.lang.UCharacter
import android.util.Log

fun Context.logi(message: String) = Log.i(UCharacter.GraphemeClusterBreak.T::class.java.simpleName, message)
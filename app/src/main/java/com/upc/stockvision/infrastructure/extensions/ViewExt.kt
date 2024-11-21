package com.upc.stockvision.infrastructure.extensions

import android.content.Context
import android.icu.lang.UCharacter
import android.util.Log
import android.widget.Toast
import com.upc.stockvision.infrastructure.utils.CustomToastBuilder
import com.upc.stockvision.infrastructure.utils.SelectedIcon

fun Context.logi(message: String) = Log.i(UCharacter.GraphemeClusterBreak.T::class.java.simpleName, message)

fun Context.toast(text: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, length).show()
//    MaterialToast.makeText(this, text, length).show();

}

 fun Context.showCustomToast(message: String, iconType: SelectedIcon) {
    CustomToastBuilder(this)
        .setMensaje(message)
        .setFoundIcon(iconType)
        .build()
}
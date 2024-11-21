package com.upc.stockvision.data.spf

import android.content.Context
import android.content.SharedPreferences

interface ISharedPreferences{
    var sharedPreferences : SharedPreferences
    var isAuthenticateUser : Boolean
}
class SharedPreferencesImpl (var context:Context, var spf: SharedPreferences) : ISharedPreferences {
    override var sharedPreferences: SharedPreferences
        get() = spf
        set(value) {}

    override var isAuthenticateUser: Boolean
        get() = spf.getBoolean(KEY_IS_AUTHENTICATE_USER, false)
        set(value) {
            spf.edit().apply {
                putBoolean(KEY_IS_AUTHENTICATE_USER, value)
                commit()
            }
        }

    companion object{
        private const val KEY_IS_AUTHENTICATE_USER = "KEY_IS_GRANTED_PERMISSIONS"
    }

}
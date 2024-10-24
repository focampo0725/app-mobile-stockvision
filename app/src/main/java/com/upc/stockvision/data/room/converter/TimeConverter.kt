package com.upc.stockvision.data.room.converter

import androidx.room.TypeConverter
import java.util.*

class TimeConverter {
    companion object{
        val FORMAT_STRING = "yyyy-MM-dd HH:mm:ss"
    }
    @TypeConverter
    fun longToDate(value: Long?): Date? {
        if(value == null)return null
        return Date(value)
    }
    @TypeConverter
    fun dateToLong(date: Date?): Long? {
        if(date == null)return null
        return date.time
    }
}
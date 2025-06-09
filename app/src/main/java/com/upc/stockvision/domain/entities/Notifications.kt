package com.upc.stockvision.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.upc.stockvision.data.room.converter.TimeConverter
import java.util.*

@Entity(tableName = Notifications.TABLE_NAME)
@TypeConverters(TimeConverter::class)
class Notifications(
    @ColumnInfo(name = "typeNotification") val typeNotification: Int,
    @ColumnInfo(name = "title") val title: String = "Hola",
    @ColumnInfo(name = "productName") val productName: String,
    @ColumnInfo(name = "warehouseName") val warehouseName: String,
    @ColumnInfo(name = "warehouseArea") val warehouseArea: String,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "createAT") val createAT: Date = Date()

) {

    companion object {
        const val TABLE_NAME = "Notifications"
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}
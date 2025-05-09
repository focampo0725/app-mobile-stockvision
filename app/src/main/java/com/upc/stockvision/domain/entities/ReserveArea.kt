package com.upc.stockvision.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.upc.stockvision.data.room.converter.TimeConverter
import java.util.*

@Entity(tableName = ReserveArea.TABLE_NAME)
@TypeConverters(TimeConverter::class)
class ReserveArea(
    @ColumnInfo(name = "categoryName") var categoryName:  String,
    @ColumnInfo(name = "productName") var productName:  String,
    @ColumnInfo(name = "quantity") var quantity:  Int,
    @ColumnInfo(name = "warehouseName") var warehouseName:  String,
    @ColumnInfo(name = "areaWarehouseName") var areaWarehouseName:  String,
    @ColumnInfo(name = "createAt") var createAt: Date,
    @ColumnInfo(name = "durationDays") var durationDays:  Int,
) {

    companion object {
        const val TABLE_NAME = "ReserveArea"
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}
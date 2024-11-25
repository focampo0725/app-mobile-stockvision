package com.upc.stockvision.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = ReserveArea.TABLE_NAME)
class ReserveArea(
    @ColumnInfo(name = "categoryName") var categoryName:  String,
    @ColumnInfo(name = "productName") var productName:  String,
    @ColumnInfo(name = "quantity") var quantity:  Int,
    @ColumnInfo(name = "warehouseName") var warehouseName:  String,
    @ColumnInfo(name = "areaWarehouseName") var areaWarehouseName:  String,
    @ColumnInfo(name = "durationDays") var durationDays:  Int,
    @ColumnInfo(name = "supplierName") var supplierName:  Int,


) {

    companion object {
        const val TABLE_NAME = "ReserveArea"
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}
package com.upc.stockvision.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Warehouse.TABLE_NAME)
class Warehouse(
    @ColumnInfo(name = "warehouseName") var warehouseName:  String,
    @ColumnInfo(name = "warehouseCode") var warehouseCode:  Int,
) {
    companion object {
        const val TABLE_NAME = "Warehouse"
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}
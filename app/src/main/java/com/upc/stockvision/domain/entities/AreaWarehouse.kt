package com.upc.stockvision.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = AreaWarehouse.TABLE_NAME)
class AreaWarehouse(
    @ColumnInfo(name = "areaWarehouseName") var areaWarehouseName:  String,
    @ColumnInfo(name = "areaWarehouseCode") var areaWarehouseCode:  Int,
    @ColumnInfo(name = "warehouseReference") var warehouseReference:  Int
) {
    companion object {
        const val TABLE_NAME = "AreaWarehouse"
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}
package com.upc.stockvision.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Supplier.TABLE_NAME)
class Supplier (
    @ColumnInfo(name = "supplierName") var supplierName:  String,
    @ColumnInfo(name = "supplierCode") var supplierCode:  Int,
        ){

    companion object {
        const val TABLE_NAME = "Supplier"
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}
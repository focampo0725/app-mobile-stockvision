package com.upc.stockvision.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.upc.stockvision.data.room.converter.TimeConverter

@Entity(tableName = Products.TABLE_NAME)
@TypeConverters(TimeConverter::class)
class Products(
    @ColumnInfo(name = "productName") var productName: String,
    @ColumnInfo(name = "categoryName") var categoryName: String,
    @ColumnInfo(name = "quantity") var quantity: Int,
    @ColumnInfo(name = "supplierName") var supplierName: String,
    @ColumnInfo(name = "warehouse") var warehouse: String,
    @ColumnInfo(name = "areaWarehouse") var areaWarehouse: String,
    @ColumnInfo(name = "photo") var photo: String,
){
    companion object {
        const val TABLE_NAME = "Products"
    }
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

}
package com.upc.stockvision.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.upc.stockvision.data.room.converter.TimeConverter
import java.util.*

@Entity(tableName = ProductMovement.TABLE_NAME)
@TypeConverters(TimeConverter::class)
class ProductMovement(
    @ColumnInfo(name = "creationUser")
    val creationUser: String,
    @ColumnInfo(name = "productName")
    val productName: String,
    @ColumnInfo(name = "initialWarehouse")
    val initialWarehouse: String,
    @ColumnInfo(name = "initialWarehouse")
    val initialAreaWarehouse: String,
    @ColumnInfo(name = "finalWarehouse")
    val finalWarehouse: String,
    @ColumnInfo(name = "finalWarehouse")
    val finalAreaWarehouse: String,
    @ColumnInfo(name = "amountMoved")
    val amountMoved: Int,
    @ColumnInfo(name = "typeMovement")
    val typeMovement: String,
    @ColumnInfo(name = "movementDate")
    val movementDate: Date = Date(),
) {
    companion object {
        const val TABLE_NAME = "ProductMovement"
    }
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}
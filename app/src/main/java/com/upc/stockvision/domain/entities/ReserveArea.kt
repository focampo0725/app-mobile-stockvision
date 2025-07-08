package com.upc.stockvision.domain.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.upc.stockvision.data.room.converter.TimeConverter
import java.util.*

@Entity(
    tableName = "ReserveArea",
    foreignKeys = [
        ForeignKey(entity = Product::class, parentColumns = ["productCode"], childColumns = ["product_code"], onDelete = CASCADE),
        ForeignKey(entity = AreaWarehouse::class, parentColumns = ["areaWarehouseCode"], childColumns = ["area_id"], onDelete = CASCADE)
    ],
    indices = [Index("product_code"), Index("area_id")]
)
@TypeConverters(TimeConverter::class)
data class ReserveArea(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "product_code")
    val productCode: String,

    @ColumnInfo(name = "area_id")
    val areaId: String,

    @ColumnInfo(name = "quantity")
    val quantity: Int,

    @ColumnInfo(name = "arrivalDate")
    val arrivalDate: String,

    @ColumnInfo(name = "createAt")
    val createAt: Date = Date()
)


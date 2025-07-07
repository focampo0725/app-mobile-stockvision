package com.upc.stockvision.domain.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.upc.stockvision.data.room.converter.TimeConverter

import java.util.*

@Entity(
    tableName = "ProductMovement",
    foreignKeys = [
        ForeignKey(entity = Product::class, parentColumns = ["id"], childColumns = ["product_id"], onDelete = CASCADE),
        ForeignKey(entity = AreaWarehouse::class, parentColumns = ["areaWarehouseCode"], childColumns = ["initial_area_id"], onDelete = CASCADE),
        ForeignKey(entity = AreaWarehouse::class, parentColumns = ["areaWarehouseCode"], childColumns = ["final_area_id"], onDelete = CASCADE)
    ],
    indices = [Index("product_id"), Index("initial_area_id"), Index("final_area_id")]
)
@TypeConverters(TimeConverter::class)
class ProductMovement(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "product_id")
    val productId: Int,

    @ColumnInfo(name = "initial_area_id")
    val initialAreaId: String,

    @ColumnInfo(name = "amountInitial")
    val amountInitial: Int,

    @ColumnInfo(name = "amountFinalInitialArea")
    val amountFinalInitialArea: Int,

    @ColumnInfo(name = "final_area_id")
    val finalAreaId: String,

    @ColumnInfo(name = "amountInitialFinalArea")
    val amountInitialFinalArea: Int,

    @ColumnInfo(name = "amountMoved")
    val amountMoved: Int,

    @ColumnInfo(name = "typeMovement")
    val typeMovement: String,

    @ColumnInfo(name = "movementDate")
    val movementDate: Date = Date()
)

package com.upc.stockvision.domain.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.upc.stockvision.data.room.converter.TimeConverter
import java.util.*

@Entity(
    tableName = "ProductMovement",
    foreignKeys = [
        ForeignKey(entity = Product::class, parentColumns = ["id"], childColumns = ["product_id"], onDelete = CASCADE),
        ForeignKey(entity = AreaWarehouse::class, parentColumns = ["id"], childColumns = ["initial_area_id"], onDelete = CASCADE),
        ForeignKey(entity = AreaWarehouse::class, parentColumns = ["id"], childColumns = ["final_area_id"], onDelete = CASCADE)
    ],
    indices = [Index("product_id"), Index("initial_area_id"), Index("final_area_id")]
)
@TypeConverters(TimeConverter::class)
data class ProductMovement(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "creationUser")
    val creationUser: String,

    @ColumnInfo(name = "product_id")
    val productId: Int,

    @ColumnInfo(name = "initial_area_id")
    val initialAreaId: Int,

    @ColumnInfo(name = "final_area_id")
    val finalAreaId: Int,

    @ColumnInfo(name = "amountMoved")
    val amountMoved: Int,

    @ColumnInfo(name = "typeMovement")
    val typeMovement: String,

    @ColumnInfo(name = "movementDate")
    val movementDate: Date = Date()
)

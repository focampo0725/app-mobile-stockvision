package com.upc.stockvision.domain.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.upc.stockvision.data.room.converter.TimeConverter

import java.util.*

@Entity(
    tableName = "ProductMovement",
    foreignKeys = [
        ForeignKey(entity = Product::class, parentColumns = ["productCode"], childColumns = ["product_code"], onDelete = CASCADE),
        ForeignKey(entity = AreaWarehouse::class, parentColumns = ["areaWarehouseCode"], childColumns = ["initial_area_id"], onDelete = CASCADE),
        ForeignKey(entity = AreaWarehouse::class, parentColumns = ["areaWarehouseCode"], childColumns = ["final_area_id"], onDelete = CASCADE)
    ],
    indices = [Index("product_code"), Index("initial_area_id"), Index("final_area_id")]
)
@TypeConverters(TimeConverter::class)
data class ProductMovement(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "product_code")
    val productCode: String,

    @ColumnInfo(name = "initial_area_id")
    val initialAreaId: String,

    //cantidad inicial en la area inicial
    @ColumnInfo(name = "amountInitial")
    val amountInitial: Int,

    //cantidad del area inicial despues del movimiento
    @ColumnInfo(name = "amountFinalInitialArea")
    val amountFinalInitialArea: Int,

    @ColumnInfo(name = "final_area_id")
    val finalAreaId: String,
//cantidad inicial en el area que se trasladara si es que tuviese
    @ColumnInfo(name = "amountInitialFinalArea")
    val amountInitialFinalArea: Int,
//cantidad movida
    @ColumnInfo(name = "amountMoved")
    val amountMoved: Int,

    @ColumnInfo(name = "typeMovement")
    val typeMovement: String,

    @ColumnInfo(name = "movementDate")
    val movementDate: Date = Date()
)


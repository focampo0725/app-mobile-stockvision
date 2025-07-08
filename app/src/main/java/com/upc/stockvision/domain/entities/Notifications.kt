package com.upc.stockvision.domain.entities

import androidx.room.*
import com.upc.stockvision.data.room.converter.TimeConverter
import java.util.*

@Entity(
    tableName = Notifications.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = Product::class,
            parentColumns = ["productCode"],
            childColumns = ["product_code"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = AreaWarehouse::class,
            parentColumns = ["areaWarehouseCode"],
            childColumns = ["area_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("product_code"), Index("area_id")]
)
@TypeConverters(TimeConverter::class)
data class Notifications(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "typeNotification")
    val typeNotification: Int,

    @ColumnInfo(name = "product_code")
    val productCode: String,

    @ColumnInfo(name = "area_id")
    val areaId: String,

    @ColumnInfo(name = "quantity")
    val quantity: Int,

    @ColumnInfo(name = "createAT")
    val createAT: Date = Date()
) {
    companion object {
        const val TABLE_NAME = "Notifications"
    }
}


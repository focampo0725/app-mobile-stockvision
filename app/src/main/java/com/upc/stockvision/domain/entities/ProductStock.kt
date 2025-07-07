package com.upc.stockvision.domain.entities

import androidx.room.*

@Entity(
    tableName = "ProductStock",
    primaryKeys = ["product_id", "area_code"],
    foreignKeys = [
        ForeignKey(
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["product_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = AreaWarehouse::class,
            parentColumns = ["areaWarehouseCode"],
            childColumns = ["area_code"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["product_id"]),
        Index(value = ["area_code"])
    ]
)
data class ProductStock(
    @ColumnInfo(name = "product_id") val productId: Int,
    @ColumnInfo(name = "area_code") val areaCode: String,
    @ColumnInfo(name = "stock") var stock: Int
)

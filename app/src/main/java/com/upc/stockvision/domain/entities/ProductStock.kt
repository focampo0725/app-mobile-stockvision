package com.upc.stockvision.domain.entities

import androidx.room.*

@Entity(
    tableName = "ProductStock",
    primaryKeys = ["product_code", "area_code"],
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
            childColumns = ["area_code"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["product_code"]),
        Index(value = ["area_code"])
    ]
)
data class ProductStock(
    @ColumnInfo(name = "product_code") val productCode: String,
    @ColumnInfo(name = "area_code") val areaCode: String,
    @ColumnInfo(name = "stock") var stock: Int,
    @ColumnInfo(name = "createdAt") val createdAt: Long = System.currentTimeMillis()
)


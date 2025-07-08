package com.upc.stockvision.domain.entities

import androidx.room.*

@Entity(
    tableName = "AreaWarehouse",
    foreignKeys = [
        ForeignKey(
            entity = Warehouse::class,
            parentColumns = ["warehouseCode"],
            childColumns = ["warehouseReference"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Category::class,
            parentColumns = ["categoryCode"],
            childColumns = ["category_code"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["warehouseReference"]),
        Index(value = ["areaWarehouseCode"], unique = true),
        Index(value = ["category_code"])
    ]
)
data class AreaWarehouse(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "areaWarehouseName")
    val areaWarehouseName: String,

    @ColumnInfo(name = "areaWarehouseCode")
    val areaWarehouseCode: String,

    @ColumnInfo(name = "warehouseReference")
    val warehouseReference: String,

    @ColumnInfo(name = "category_code")
    val categoryCode: String // ← Nueva relación con la categoría
)

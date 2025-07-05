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
        )
    ],
    indices = [
        Index(value = ["warehouseReference"]),
        Index(value = ["areaWarehouseCode"], unique = true) // ← ahora es único
    ]
)
data class AreaWarehouse(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "areaWarehouseName")
    val areaWarehouseName: String,

    @ColumnInfo(name = "areaWarehouseCode")
    val areaWarehouseCode: String, // ← este es UNIQUE

    @ColumnInfo(name = "warehouseReference")
    val warehouseReference: String
)

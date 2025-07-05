package com.upc.stockvision.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Warehouse",
    indices = [Index(value = ["warehouseCode"], unique = true)]
)
data class Warehouse(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "warehouseName")
    val warehouseName: String,

    @ColumnInfo(name = "warehouseCode")
    val warehouseCode: String
)

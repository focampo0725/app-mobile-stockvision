package com.upc.stockvision.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Supplier",
    indices = [Index(value = ["supplierCode"], unique = true)]
)
data class Supplier(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "supplierName")
    val supplierName: String,

    @ColumnInfo(name = "supplierCode")
    val supplierCode: Int
)
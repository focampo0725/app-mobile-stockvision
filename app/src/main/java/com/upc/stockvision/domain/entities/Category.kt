package com.upc.stockvision.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Category",
    indices = [Index(value = ["categoryCode"], unique = true)]
)
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "categoryName")
    val categoryName: String,

    @ColumnInfo(name = "categoryCode")
    val categoryCode: String
)

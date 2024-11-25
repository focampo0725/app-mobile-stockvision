package com.upc.stockvision.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Category.TABLE_NAME)
class Category(
    @ColumnInfo(name = "categoryName") var categoryName: String,
    @ColumnInfo(name = "categoryCode") var categoryCode: Int
) {


    companion object {
        const val TABLE_NAME = "Category"
    }
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}
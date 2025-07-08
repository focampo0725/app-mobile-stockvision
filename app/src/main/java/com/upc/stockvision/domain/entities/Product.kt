package com.upc.stockvision.domain.entities

import androidx.room.*

@Entity(
    tableName = "Product",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["categoryCode"],
            childColumns = ["category_code"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Supplier::class,
            parentColumns = ["supplierCode"],
            childColumns = ["supplier_code"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("category_code"), Index("supplier_code"), Index(value = ["productCode"], unique = true)]
)
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "productCode")
    val productCode: String,

    @ColumnInfo(name = "productName")
    val productName: String,

    @ColumnInfo(name = "photo")
    val photo: String,

    @ColumnInfo(name = "category_code")
    val categoryCode: String,

    @ColumnInfo(name = "supplier_code")
    val supplierCode: String
)

package com.upc.stockvision.domain.dto

import com.google.gson.annotations.SerializedName

data class IdentityUserDTO(
    @SerializedName("name")
    val name : String,
    @SerializedName("fatherSurname")
    val fatherSurname : String,
    @SerializedName("motherSurname ")
    val motherSurname : String
)

data class CategoryDTO(
    @SerializedName("codeCategory")
    val codeCategory : Int,
    @SerializedName("categoryName")
    val categoryName : String
)

data class SupplierDTO(
    @SerializedName("codeCategory")
    val codeSupplier : Int,
    @SerializedName("categoryName")
    val supplierName : String
)

data class WarehouseDTO(
    @SerializedName("codeCategory")
    val codeWarehouse : Int,
    @SerializedName("categoryName")
    val warehouseName : String
)

data class AreaWarehouseDTO(
    @SerializedName("codeCategory")
    val codeAreaWarehouse : Int,
    @SerializedName("categoryName")
    val areaWarehouseName : String
)

data class ProductDTO(
    @SerializedName("productName")
    val productName: String,
    @SerializedName("categoryName")
    val categoryName: String,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("supplierName")
    val supplierName: String,
    @SerializedName("warehouse")
    val warehouse: String,
    @SerializedName("areaWarehouse")
    val areaWarehouse: String,
    @SerializedName("photo")
    val photo: String
)

package com.upc.stockvision.domain.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.Date

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

data class TypeMovementDTO(
    @SerializedName("codeTypeMovement")
    val codeTypeMovement : Int,
    @SerializedName("typeMovementName")
    val typeMovementName : String
)

data class SupplierDTO(
    @SerializedName("codeCategory")
    val codeSupplier : Int,
    @SerializedName("categoryName")
    val supplierName : String
): Serializable

data class WarehouseDTO(
    @SerializedName("codeCategory")
    val codeWarehouse : Int,
    @SerializedName("categoryName")
    val warehouseName : String
): Serializable

data class AreaWarehouseDTO(
    @SerializedName("codeCategory")
    val codeAreaWarehouse : Int,
    @SerializedName("categoryName")
    val areaWarehouseName : String
): Serializable

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
): Serializable

data class ProductOnDetailDTO(
    @SerializedName("idProduct")
    val idProduct: Int,
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
): Serializable

data class ReserveAreaDTO(
    val id: Int,
    val categoryName: String,
    val productName: String,
    val quantity: Int,
    val warehouseName: String,
    val areaWarehouseName: String,
    val createAt: String,
    val durationDays: Int
)

data class ProductMovementDTO(
    val idMovemet : Int,
    val creationUser : String,
    val productName : String,
    val initialWarehouse: String,
    val initialAreaWarehouse: String,
    val finalWarehouse: String,
    val finalAreaWarehouse: String,
    val amountMoved : Int,
    val typeMovement: String,
    val movementDate : String

)

data class NotificationDTO(
    val typeNotification : Int,
    val title : String,
    val productName : String,
    val warehouseName : String,
    val warehouseArea : String,
    val quantity : Int,
    val date: String
)
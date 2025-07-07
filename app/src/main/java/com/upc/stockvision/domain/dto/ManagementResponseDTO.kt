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
    val codeCategory : String,
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
    val codeSupplier : String,
    @SerializedName("categoryName")
    val supplierName : String
): Serializable

data class WarehouseDTO(
    val codeWarehouse : String,
    val warehouseName : String
): Serializable

data class AreaWarehouseDTO(
    @SerializedName("codeCategory")
    val codeAreaWarehouse : String,
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
    val idProduct: Int,
    val productName: String,
    val categoryName: String,
    val quantity: Int,
    val supplierName: String,
    val warehouse: String,
    val areaWarehouse: String,
    val photo: String
): Serializable

data class ReserveDetailDTO(
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
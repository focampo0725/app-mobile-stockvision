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
    val codeTypeMovement : Int,
    val typeMovementName : String
)




data class SupplierDTO(
    val codeSupplier : String,
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
    val idProduct: String,
    val productName: String,
    val categoryName: String,
    val quantity: Int,
    val supplierName: String,
    val warehouse: String,
    val warehouseCode: String,
    val areaWarehouse: String,
    val areaWarehouseCode: String,
    val photo: String
): Serializable

data class ReservationDetailDTO(
    val reservationId: Int,
    val productCode : String,
    val productName: String,
    val photo: String,
    val quantityReserved: Int,
    val areaWarehouseCode: String,
    val areaWarehouseName: String,
    val warehouseCode: String,
    val warehouseName: String,
    val arrivalDate: String,
    val state : Int
): Serializable



data class ProductMovementDTO(
    val idMovemet : Int,
    val productName : String,
    val photo : String,
    val initialWarehouse: String,
    val initialAreaWarehouse: String,
    val quantityInitial : Int,
    val quantityInitialArea : Int,
    val finalWarehouse: String,
    val finalAreaWarehouse: String,
    val quantityInitialFinalArea : Int,
    val amountMoved : Int,
    val typeMovement: String,
    val movementDate : String
): Serializable

data class NotificationDTO(
    val typeNotification : Int,
    val title : String,
    val productName : String,
    val warehouseName : String,
    val warehouseArea : String,
    val quantity : Int,
    val date: String
)
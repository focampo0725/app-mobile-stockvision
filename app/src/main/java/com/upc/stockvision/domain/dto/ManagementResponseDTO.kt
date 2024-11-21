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
package com.upc.stockvision.domain.dto

import com.google.gson.annotations.SerializedName

data class CategoryDTO(
    @SerializedName("codeCategory")
    val codeCategory : Int,
    @SerializedName("categoryName")
    val categoryName : String
)

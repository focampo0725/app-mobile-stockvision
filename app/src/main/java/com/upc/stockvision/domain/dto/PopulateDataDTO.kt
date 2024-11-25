package com.upc.stockvision.domain.dto

data class IdentityUserPopulateDTO(
    val identityUser : String,
    val name : String,
    val fatherSurname : String,
    val motherSurname : String,
    val password : String
)


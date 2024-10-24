package com.upc.stockvision.domain.dto

data class TodoResponseDTO (
    val userId: Long,
    val id: Long,
    val title: String,
    val completed: Boolean
)
package com.upc.stockvision.domain.dto


data class ResponseGenericDTO<T>(
    val content: List<T>,
    val isValid: Boolean,
    val exceptions: List<String>
)
package com.upc.stockvision.domain.dto


data class ResponseGenericDTO<T>(
    val content: List<T>,
    val isValid: Boolean,
    val exceptions: List<String>
)

data class ResponseGenericv2DTO<T>(
    val content: T,
    val isValid: Boolean,
    val exceptions: String
)
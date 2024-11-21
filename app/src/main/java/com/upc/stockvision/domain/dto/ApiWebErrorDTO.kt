package com.upc.stockvision.domain.dto

data class ApiWebErrorDTO(val success : Boolean, val error : String)

data class APITokenGeneratorWebErrorDTO (
    var success: Boolean,
    var exceptions: List<WebExceptionDTO>
)

data class WebExceptionDTO (
    val code: String,
    val description: String
)

data class WebSpringbootException (
    val timestamp: String,
    val status: Long,
    val error: String,
    val trace: String,
    val message: String,
    val path: String
)

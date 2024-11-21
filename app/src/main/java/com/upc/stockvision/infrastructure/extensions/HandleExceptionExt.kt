package com.upc.stockvision.infrastructure.extensions


import com.google.gson.Gson
import com.upc.stockvision.domain.dto.APITokenGeneratorWebErrorDTO
import com.upc.stockvision.domain.dto.ApiWebErrorDTO
import com.upc.stockvision.domain.dto.WebSpringbootException

/**
 * mapa de excepciones para las web apis
 */

val exceptionCommands : Map<Class<*>, ((Any) -> String?)> = mapOf(
    ApiWebErrorDTO::class.java to {(it as ApiWebErrorDTO).error},
    APITokenGeneratorWebErrorDTO::class.java to {(it as APITokenGeneratorWebErrorDTO).exceptions.firstOrNull()?.description},
    WebSpringbootException::class.java to {(it as WebSpringbootException).trace}
)


fun Throwable.castError(exceptionCommandList : Map<Class<*>, ((Any) -> String?)>) : String?{
    var response : String? = null
    if (this is com.jakewharton.retrofit2.adapter.rxjava2.HttpException)  // retrofit2 exception
    {
        val exceptionResponse = String(this.response().errorBody()?.bytes() ?: ByteArray(0))
        response = exceptionCommandList.map {
            var response : String? = null
            try {
                val parsedException = Gson().fromJson(exceptionResponse, it.key)
                response = it.value(parsedException)
            }catch (e : Exception){

            }
            response
        }.filter { it != null }.firstOrNull()
    }
    return response
}


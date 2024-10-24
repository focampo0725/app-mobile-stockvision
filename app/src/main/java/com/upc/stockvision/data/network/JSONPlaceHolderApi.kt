package com.upc.stockvision.data.network

import com.upc.stockvision.domain.dto.TodoResponseDTO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path


interface JSONPlaceHolderApi {

    //    {{URL}}/todos/1
    @GET("/todos/{id}")
    fun getCompanies(@Path("id") id : Long): Single<TodoResponseDTO>


//



}
package com.upc.stockvision.data.network

import com.upc.stockvision.domain.dto.CategoryDTO
import com.upc.stockvision.domain.dto.IdentityUserDTO
import com.upc.stockvision.domain.dto.ResponseGenericDTO
import com.upc.stockvision.domain.dto.ResponseGenericv2DTO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ManagementApi {

    @GET("requestLogin")
    fun getUser(
        @Query("identityDocument") identityDocument: String,
        @Query("password") password: String,
    ): Single<ResponseGenericv2DTO<IdentityUserDTO>>

    @GET("requestCategory")
    fun getCategoryList(
    ): Single<ResponseGenericDTO<CategoryDTO>>
}
package com.upc.stockvision.di.module

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.upc.stockvision.data.network.JSONPlaceHolderApi
import com.upc.stockvision.data.network.ManagementApi
import com.upc.stockvision.di.qualifiers.ManagementApiQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideQupteApiClient(retrofit: Retrofit): JSONPlaceHolderApi = retrofit.create(JSONPlaceHolderApi::class.java)

    ////Provide Login

    @Provides
    @Singleton
    @ManagementApiQualifier
    fun provideSignInOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @ManagementApiQualifier
    fun provideSignInRetrofit(@ManagementApiQualifier client: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://extendsclass.com/mock/rest/8e9941fcfbfe86bf704af8a8d7c4207a/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSignInApiClient(@ManagementApiQualifier retrofit: Retrofit): ManagementApi = retrofit.create(ManagementApi::class.java)


//

}
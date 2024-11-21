package com.upc.stockvision.di.module

import android.content.Context
import android.content.SharedPreferences
import com.upc.stockvision.data.spf.ISharedPreferences
import com.upc.stockvision.data.spf.SharedPreferencesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule @Inject constructor() {

    @Provides
    @Singleton
    fun provideSPF(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideISPF(
        @ApplicationContext context: Context,
        sharedPreferences: SharedPreferences
    ): ISharedPreferences = SharedPreferencesImpl(context = context, spf = sharedPreferences)
}
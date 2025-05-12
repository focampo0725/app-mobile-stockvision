package com.upc.stockvision.di.module

import android.content.Context
import androidx.room.Room
import com.upc.stockvision.data.room.StockVisionDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    private val DATABASE_NAME = "stockvision_database"
    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) = Room.databaseBuilder(context, StockVisionDB::class.java, DATABASE_NAME).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideTodoDao(stockVisionDB:StockVisionDB) = stockVisionDB.todoDao()

    @Singleton
    @Provides
    fun provideSessionDao(stockVisionDB:StockVisionDB) = stockVisionDB.sessionDao()

    @Singleton
    @Provides
    fun provideIdentityUserDao(stockVisionDB:StockVisionDB) = stockVisionDB.identityUserDao()

    @Singleton
    @Provides
    fun provideProductsDao(stockVisionDB:StockVisionDB) = stockVisionDB.productsDao()

    @Singleton
    @Provides
    fun provideAreaWarehouseDao(stockVisionDB:StockVisionDB) = stockVisionDB.areaWarehouseDao()

    @Singleton
    @Provides
    fun provideWarehouseDao(stockVisionDB:StockVisionDB) = stockVisionDB.warehouseDao()

    @Singleton
    @Provides
    fun provideSupplierDao(stockVisionDB:StockVisionDB) = stockVisionDB.supplierDao()
    @Singleton
    @Provides
    fun provideReserveAreaDao(stockVisionDB:StockVisionDB) = stockVisionDB.reserveAreaDao()
    @Singleton
    @Provides
    fun provideProductMovementDao(stockVisionDB:StockVisionDB) = stockVisionDB.productMovementDao()

    @Singleton
    @Provides
    fun provideCategoryDao(stockVisionDB:StockVisionDB) = stockVisionDB.categoryDao()
}
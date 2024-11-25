package com.upc.stockvision.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.upc.stockvision.domain.entities.Products
import com.upc.stockvision.domain.entities.Supplier

@Dao
interface ProductsDao : BaseDao<Products> {
    @Query("SELECT * FROM Products")
    fun getAll(): List<Products>
}
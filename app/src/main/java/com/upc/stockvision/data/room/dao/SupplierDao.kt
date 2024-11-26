package com.upc.stockvision.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.upc.stockvision.domain.entities.Category
import com.upc.stockvision.domain.entities.Supplier

@Dao
interface SupplierDao : BaseDao<Supplier> {
    @Query("SELECT * FROM Supplier")
    fun getAll(): List<Supplier>

    @Query("DELETE FROM Supplier")
    fun deleteAll()
}
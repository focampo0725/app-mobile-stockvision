package com.upc.stockvision.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.upc.stockvision.domain.entities.Category

@Dao
interface CategoryDao : BaseDao<Category> {

    @Query("SELECT * FROM Category")
    fun getAll(): List<Category>

    @Query("DELETE FROM Category")
    fun deleteAll()

}
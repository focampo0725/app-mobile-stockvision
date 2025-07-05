package com.upc.stockvision.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.upc.stockvision.domain.entities.IdentityUser
import com.upc.stockvision.domain.entities.ProductStock
import com.upc.stockvision.domain.entities.Todo

@Dao
interface ProductStockDao : BaseDao<ProductStock>  {
    @Query("DELETE FROM ProductStock")
    fun deleteAll()
}


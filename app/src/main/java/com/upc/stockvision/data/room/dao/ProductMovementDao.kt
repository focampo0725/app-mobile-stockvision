package com.upc.stockvision.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.upc.stockvision.domain.entities.ProductMovement


@Dao
interface ProductMovementDao : BaseDao<ProductMovement> {

    @Query("SELECT * FROM ProductMovement")
    fun getAllProductMovement(): List<ProductMovement>
}
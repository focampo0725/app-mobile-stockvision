package com.upc.stockvision.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.upc.stockvision.domain.entities.Products
import com.upc.stockvision.domain.entities.ReserveArea

@Dao
interface ReserveAreaDao : BaseDao<ReserveArea> {

    @Query("SELECT * FROM ReserveArea")
    fun getAllReserve(): List<ReserveArea>
}
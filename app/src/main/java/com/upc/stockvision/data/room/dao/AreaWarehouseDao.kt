package com.upc.stockvision.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.upc.stockvision.domain.entities.AreaWarehouse

@Dao
interface AreaWarehouseDao : BaseDao<AreaWarehouse> {
    @Query("SELECT * FROM AreaWarehouse WHERE warehouseReference = :code")
    fun getAll(code : Int): List<AreaWarehouse>

    @Query("DELETE FROM AreaWarehouse")
    fun deleteAll()
}
package com.upc.stockvision.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.upc.stockvision.domain.entities.Supplier
import com.upc.stockvision.domain.entities.Warehouse

@Dao
interface WarehouseDao : BaseDao<Warehouse>{
    @Query("SELECT * FROM Warehouse")
    fun getAll(): List<Warehouse>

    @Query("SELECT warehouseCode FROM Warehouse WHERE warehouseName = :name")
    fun getWarehouseCodeByName(name: String): Int
}
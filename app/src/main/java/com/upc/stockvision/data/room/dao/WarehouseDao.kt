package com.upc.stockvision.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.upc.stockvision.domain.entities.Supplier
import com.upc.stockvision.domain.entities.Warehouse

@Dao
interface WarehouseDao : BaseDao<Warehouse>{
    @Query("SELECT * FROM Warehouse")
    fun getAll(): List<Warehouse>

    @Query("""
    SELECT DISTINCT w.* FROM Warehouse w
    INNER JOIN AreaWarehouse a ON w.warehouseCode = a.warehouseReference
    WHERE a.category_code = :categoryCode""")
    fun getWarehousesByCategory(categoryCode: String): List<Warehouse>

    @Query("SELECT warehouseCode FROM Warehouse WHERE warehouseName = :name")
    fun getWarehouseCodeByName(name: String): String



    @Query("SELECT * FROM Warehouse WHERE warehouseCode = :code LIMIT 1")
    fun getByCode(code: String): Warehouse?
    @Query("DELETE FROM Warehouse")
    fun deleteAll()
}
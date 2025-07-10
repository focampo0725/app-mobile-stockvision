package com.upc.stockvision.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.upc.stockvision.domain.entities.AreaWarehouse

@Dao
interface AreaWarehouseDao : BaseDao<AreaWarehouse> {
    @Query("SELECT * FROM AreaWarehouse WHERE warehouseReference = :code")
    fun getAll(code : String): List<AreaWarehouse>

    @Query("""
    SELECT * FROM AreaWarehouse 
    WHERE warehouseReference = :warehouseCode AND category_code = :categoryCode """)
    fun getAreasByWarehouseAndCategory(warehouseCode: String, categoryCode: String): List<AreaWarehouse>
    @Query("SELECT * FROM AreaWarehouse WHERE areaWarehouseCode = :code LIMIT 1")
    fun getByCode(code: String): AreaWarehouse?

    @Query("DELETE FROM AreaWarehouse")
    fun deleteAll()
    @Query("SELECT * FROM AreaWarehouse WHERE warehouseReference = :warehouseCode")
    fun getAreasByWarehouse(warehouseCode: String):List<AreaWarehouse>
}
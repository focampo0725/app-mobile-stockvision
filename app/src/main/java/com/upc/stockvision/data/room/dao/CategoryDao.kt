package com.upc.stockvision.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.upc.stockvision.domain.entities.Category

@Dao
interface CategoryDao : BaseDao<Category> {

    @Query("""
    SELECT DISTINCT c.* FROM Category c
    INNER JOIN Product p ON c.categoryCode = p.category_code
    INNER JOIN ProductStock ps ON ps.product_code = p.productCode
    INNER JOIN AreaWarehouse aw ON aw.areaWarehouseCode = ps.area_code
    INNER JOIN Warehouse w ON w.warehouseCode = aw.warehouseReference
    WHERE (:warehouseCode IS NULL OR w.warehouseCode = :warehouseCode)
    """)
    fun getCategoriesByWarehouseOrAll(warehouseCode: String?): List<Category>


    @Query("SELECT * FROM Category")
    fun getAll(): List<Category>

    @Query("DELETE FROM Category")
    fun deleteAll()

}
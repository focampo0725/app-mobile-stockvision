package com.upc.stockvision.data.room.dao

import androidx.room.*
import com.upc.stockvision.domain.entities.ProductStock

@Dao
interface ProductStockDao : BaseDao<ProductStock>  {

    // Obtener stock por producto y área
    @Query("""
        SELECT * FROM ProductStock 
        WHERE product_code = :productCode AND area_code = :areaCode 
        LIMIT 1
    """)
    fun getByProductAndArea(productCode: String, areaCode: String): ProductStock?


    @Update
    fun update(productStock: ProductStock)
    @Query("DELETE FROM ProductStock")
    fun deleteAll()

    @Query("""
    UPDATE ProductStock 
    SET stock = COALESCE(:stock, stock)
    WHERE product_code = :productCode AND area_code = :areaId""")
    fun updateStockData(productCode: String, areaId: String, stock: Int?)

    @Query("DELETE FROM ProductStock WHERE product_code = :productCode AND area_code = :areaId")
    fun deleteByProductAndArea(productCode: String, areaId: String)
}


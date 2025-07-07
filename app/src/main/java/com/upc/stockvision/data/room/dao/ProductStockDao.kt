package com.upc.stockvision.data.room.dao

import androidx.room.*
import com.upc.stockvision.domain.entities.IdentityUser
import com.upc.stockvision.domain.entities.ProductStock
import com.upc.stockvision.domain.entities.Todo

@Dao
interface ProductStockDao : BaseDao<ProductStock>  {

    // Obtener stock por producto y área
    @Query("""
        SELECT * FROM ProductStock 
        WHERE product_id = :productId AND area_code = :areaCode 
        LIMIT 1
    """)
    fun getByProductAndArea(productId: Int, areaCode: String): ProductStock?

    // Actualizar stock existente
    @Update
    fun update(productStock: ProductStock)
    @Query("DELETE FROM ProductStock")
    fun deleteAll()
}


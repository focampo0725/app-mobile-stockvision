package com.upc.stockvision.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.upc.stockvision.domain.entities.Products
import com.upc.stockvision.domain.entities.Supplier

@Dao
interface ProductsDao : BaseDao<Products> {
    @Query("SELECT * FROM Products")
    fun getAll(): List<Products>

    @Query("DELETE FROM Products")
    fun deleteAll()

    @Query("""
        UPDATE Products 
        SET productName = COALESCE(:productName, productName),
            categoryName = COALESCE(:categoryName, categoryName),
            quantity = COALESCE(:quantity, quantity),
            supplierName = COALESCE(:supplierName, supplierName),
            warehouse = COALESCE(:warehouse, warehouse),
            areaWarehouse = COALESCE(:areaWarehouse, areaWarehouse),
            photo = COALESCE(:photo, photo)
        WHERE id = :id
    """)
    fun updateProduct(
        id: Int,
        productName: String?,
        categoryName: String?,
        quantity: Int?,
        supplierName: String?,
        warehouse: String?,
        areaWarehouse: String?,
        photo: String?
    )
}
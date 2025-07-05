package com.upc.stockvision.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.upc.stockvision.domain.entities.Product


@Dao
interface ProductDao : BaseDao<Product> {
    @Query("SELECT * FROM Product")
    fun getAll(): List<Product>

    @Query("SELECT * FROM Product WHERE id = :idProduct")
    fun getOnlyProduct(idProduct : Int): Product

    @Query("SELECT * FROM Product WHERE productName = :productName")
    fun getProductByName(productName: String): Product


    @Query("SELECT * FROM Product WHERE categoryName = :categoryName")
    fun getProductsByCategory(categoryName: String): List<Product>

    @Query("DELETE FROM Product")
    fun deleteAll()

    @Query("""
        UPDATE Product 
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

    @Query("""UPDATE Product SET quantity = :quantity  WHERE id = :id""")
    fun updateQuantityForMovement(id : Int, quantity: Int)
}
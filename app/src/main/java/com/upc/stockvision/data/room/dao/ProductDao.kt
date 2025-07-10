package com.upc.stockvision.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.upc.stockvision.domain.dto.ProductOnDetailDTO
import com.upc.stockvision.domain.entities.Product


@Dao
interface ProductDao : BaseDao<Product> {
    @Query("SELECT * FROM Product")
    fun getAll(): List<Product>
    @Query("SELECT * FROM Product WHERE productCode = :productCode LIMIT 1")
    suspend fun getProductByCode(productCode: String): Product?
    @Query("SELECT COUNT(*) FROM Product WHERE productCode = :code")
    fun countProductCode(code: String): Int


    @Query("SELECT * FROM Product WHERE productCode = :productCode")
    fun getByProductCode(productCode: String): Product

    @Query("""
    SELECT 
        p.productCode AS idProduct,
        p.productName AS productName,
        c.categoryName AS categoryName,
        ps.stock AS quantity,
        s.supplierName AS supplierName,
        w.warehouseName AS warehouse,
        w.warehouseCode AS warehouseCode,
        aw.areaWarehouseName AS areaWarehouse,
        aw.areaWarehouseCode AS areaWarehouseCode,
        p.photo AS photo
    FROM Product p
    INNER JOIN Category c ON p.category_code = c.categoryCode
    INNER JOIN Supplier s ON p.supplier_code = s.supplierCode
    INNER JOIN ProductStock ps ON p.productCode = ps.product_code
    INNER JOIN AreaWarehouse aw ON ps.area_code = aw.areaWarehouseCode
    INNER JOIN Warehouse w ON aw.warehouseReference = w.warehouseCode
    WHERE p.category_code = :categoryCode""")
    fun getProductsByCategory(categoryCode: String): List<ProductOnDetailDTO>

    @Query("""
    SELECT 
        p.productCode AS idProduct,
        p.productName AS productName,
        c.categoryName AS categoryName,
        ps.stock AS quantity,
        s.supplierName AS supplierName,
        w.warehouseName AS warehouse,
        w.warehouseCode AS warehouseCode,
        aw.areaWarehouseName AS areaWarehouse,
        aw.areaWarehouseCode AS areaWarehouseCode,
        p.photo AS photo
    FROM Product p
    INNER JOIN Category c ON p.category_code = c.categoryCode
    INNER JOIN Supplier s ON p.supplier_code = s.supplierCode
    INNER JOIN ProductStock ps ON p.productCode = ps.product_code
    INNER JOIN AreaWarehouse aw ON ps.area_code = aw.areaWarehouseCode
    INNER JOIN Warehouse w ON aw.warehouseReference = w.warehouseCode""")
    fun getAllProductDetails(): List<ProductOnDetailDTO>



    @Query("""
    UPDATE Product 
    SET productName = COALESCE(:productName, productName)
    WHERE productCode = :productCode""")
    fun updateProductData(productCode: String, productName: String?)

    @Query("""
    UPDATE Product 
    SET category_code = COALESCE(:categoryCode, category_code)
    WHERE productCode = :productCode""")
    fun updateCategory(productCode: String, categoryCode: String?)



    @Query("DELETE FROM Product")
    fun deleteAll()



}
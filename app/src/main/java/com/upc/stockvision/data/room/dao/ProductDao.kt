package com.upc.stockvision.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.upc.stockvision.domain.dto.ProductOnDetailDTO
import com.upc.stockvision.domain.entities.Category
import com.upc.stockvision.domain.entities.Product


@Dao
interface ProductDao : BaseDao<Product> {
    @Query("SELECT * FROM Product")
    fun getAll(): List<Product>

    @Query("SELECT * FROM Product WHERE id = :idProduct")
    fun getOnlyProduct(idProduct : Int): Product

    @Query("SELECT * FROM Product WHERE productName = :productName")
    fun getProductByName(productName: String): Product


    @Query("""
    SELECT 
        p.id AS idProduct,
        p.productName AS productName,
        c.categoryName AS categoryName,
        ps.stock AS quantity,
        s.supplierName AS supplierName,
        w.warehouseName AS warehouse,
        aw.areaWarehouseName AS areaWarehouse,
        p.photo AS photo
    FROM Product p
    INNER JOIN Category c ON p.category_code = c.categoryCode
    INNER JOIN Supplier s ON p.supplier_code = s.supplierCode
    INNER JOIN ProductStock ps ON p.id = ps.product_id
    INNER JOIN AreaWarehouse aw ON ps.area_code = aw.areaWarehouseCode
    INNER JOIN Warehouse w ON aw.warehouseReference = w.warehouseCode
    WHERE p.category_code = :categoryCode""")
    fun getProductsByCategory(categoryCode: String): List<ProductOnDetailDTO>


    @Query("""
    SELECT 
        p.id AS idProduct,
        p.productName AS productName,
        c.categoryName AS categoryName,
        ps.stock AS quantity,
        s.supplierName AS supplierName,
        w.warehouseName AS warehouse,
        aw.areaWarehouseName AS areaWarehouse,
        p.photo AS photo
    FROM Product p
    INNER JOIN Category c ON p.category_code = c.categoryCode
    INNER JOIN Supplier s ON p.supplier_code = s.supplierCode
    INNER JOIN ProductStock ps ON p.id = ps.product_id
    INNER JOIN AreaWarehouse aw ON ps.area_code = aw.areaWarehouseCode
    INNER JOIN Warehouse w ON aw.warehouseReference = w.warehouseCode""")
    fun getAllProductDetails(): List<ProductOnDetailDTO>


    @Query("DELETE FROM Product")
    fun deleteAll()



}
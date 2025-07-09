package com.upc.stockvision.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.upc.stockvision.domain.dto.ProductMovementDTO
import com.upc.stockvision.domain.dto.ProductOnDetailDTO
import com.upc.stockvision.domain.entities.ProductMovement


@Dao
interface ProductMovementDao : BaseDao<ProductMovement> {

    @Query("SELECT * FROM ProductMovement")
    fun getAllProductMovement(): List<ProductMovement>

    @Query("""
SELECT 
    pm.id AS idMovemet,
    p.productName AS productName,
    p.photo AS photo,
    awInit.areaWarehouseName AS initialAreaWarehouse,
    wInit.warehouseName AS initialWarehouse,
    pm.amountInitial AS quantityInitial,
    pm.amountFinalInitialArea AS quantityInitialArea,
    awFinal.areaWarehouseName AS finalAreaWarehouse,
    wFinal.warehouseName AS finalWarehouse,
    pm.amountInitialFinalArea AS quantityInitialFinalArea,
    pm.amountMoved AS amountMoved,
    pm.typeMovement AS typeMovement,
    pm.movementDate AS movementDate
    FROM ProductMovement pm
    JOIN Product p ON p.productCode = pm.product_code
    JOIN AreaWarehouse awInit ON awInit.areaWarehouseCode = pm.initial_area_id
    JOIN Warehouse wInit ON wInit.warehouseCode = awInit.warehouseReference
    JOIN AreaWarehouse awFinal ON awFinal.areaWarehouseCode = pm.final_area_id
    JOIN Warehouse wFinal ON wFinal.warehouseCode = awFinal.warehouseReference""")
    fun getAllMovementsWithDetails(): List<ProductMovementDTO>

}

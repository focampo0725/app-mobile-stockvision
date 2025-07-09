package com.upc.stockvision.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.upc.stockvision.domain.dto.ReservationDetailDTO

import com.upc.stockvision.domain.entities.ReserveArea

@Dao
interface ReserveAreaDao : BaseDao<ReserveArea> {

    @Query("SELECT * FROM ReserveArea")
    fun getAllReserve(): List<ReserveArea>

    @Query("DELETE FROM ReserveArea")
    fun deleteAll()

    @Query(
        """
    SELECT
        r.id AS reservationId,
        p.productName,
        p.photo,
        r.quantity AS quantityReserved,
        r.area_id As areaWarehouseCode,
        aw.areaWarehouseName AS areaWarehouseName,
        w.warehouseCode AS warehouseCode,
        w.warehouseName AS warehouseName,
        r.arrivalDate
    FROM ReserveArea r
    INNER JOIN Product p ON p.id = r.product_code
    INNER JOIN AreaWarehouse aw ON aw.areaWarehouseCode = r.area_id
    INNER JOIN Warehouse w ON w.warehouseCode = aw.warehouseReference""")
    fun getReservationDetailById(): List<ReservationDetailDTO>

}

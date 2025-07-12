package com.upc.stockvision.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.upc.stockvision.domain.dto.ReservationDetailDTO

import com.upc.stockvision.domain.entities.ReserveArea

@Dao
interface ReserveAreaDao : BaseDao<ReserveArea> {
    @Query("SELECT * FROM ReserveArea WHERE id = :id")
    fun getAReserve(id: Int): ReserveArea
    @Query("SELECT * FROM ReserveArea")
    fun getAllReserve(): List<ReserveArea>

    @Query("DELETE FROM ReserveArea")
    fun deleteAll()

    @Query("""
    UPDATE ReserveArea
    SET state = :state
    WHERE id = :reserveId
""")
    fun updateState(reserveId: Int, state: Int)
    @Query(
        """
    SELECT
        r.id AS reservationId,
        p.productCode AS productCode,
        p.productName,
        p.photo,
        r.quantity AS quantityReserved,
        r.area_id As areaWarehouseCode,
        aw.areaWarehouseName AS areaWarehouseName,
        w.warehouseCode AS warehouseCode,
        w.warehouseName AS warehouseName,
        r.arrivalDate,
        r.state
    FROM ReserveArea r
    INNER JOIN Product p ON p.productCode = r.product_code
    INNER JOIN AreaWarehouse aw ON aw.areaWarehouseCode = r.area_id
    INNER JOIN Warehouse w ON w.warehouseCode = aw.warehouseReference""")
    fun getReservationDetail(): List<ReservationDetailDTO>

}

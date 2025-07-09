package com.upc.stockvision.data.room.dao

import androidx.room.Dao
import androidx.room.Query

import com.upc.stockvision.domain.entities.ReserveArea

@Dao
interface ReserveAreaDao : BaseDao<ReserveArea> {

    @Query("SELECT * FROM ReserveArea")
    fun getAllReserve(): List<ReserveArea>

    @Query("DELETE FROM ReserveArea")
    fun deleteAll()

//    @Query(
//        """
//    SELECT
//        r.id AS reservationId,
//        p.productName,
//        p.photo,
//        r.quantity,
//        aw.areaWarehouseName AS reservedArea,
//        w.warehouseName AS warehouse,
//        r.arrivalDate
//    FROM ReserveArea r
//    INNER JOIN Product p ON p.id = r.product_id
//    INNER JOIN AreaWarehouse aw ON aw.areaWarehouseCode = r.area_id
//    INNER JOIN Warehouse w ON w.warehouseCode = aw.warehouseReference
//    WHERE r.id = :reservationId """
//    )
//    fun getReservationDetailById(reservationId: Int): ReservationDetailDTO

}

package com.upc.stockvision.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.upc.stockvision.domain.dto.NotificationDTO
import com.upc.stockvision.domain.entities.Notifications
import com.upc.stockvision.domain.entities.Product
import com.upc.stockvision.domain.entities.ReserveArea

@Dao
interface NotificationsDao : BaseDao<Notifications> {

    @Query("SELECT * FROM Notifications ORDER BY id DESC")
    fun getAllNotificatios(): List<Notifications>

    @Query("DELETE FROM Notifications")
    fun deleteAll()

    @Query("DELETE FROM sqlite_sequence WHERE name='Notifications'")
    fun deleteAllSequence()

    @Query(
        """
        SELECT 
            n.typeNotification AS typeNotification,
            CASE 
                WHEN n.typeNotification = 1 THEN 'Alerta de stock bajo'
                WHEN n.typeNotification = 2 THEN 'Producto vencido'
                ELSE 'Notificación'
            END AS title,
            p.productName AS productName,
            w.warehouseName AS warehouseName,
            a.areaWarehouseName AS warehouseArea,
            n.quantity AS quantity,
            strftime('%d/%m/%Y %H:%M', n.createAT / 1000, 'unixepoch') AS date
        FROM Notifications n
        INNER JOIN Product p ON n.product_code = p.productCode
        INNER JOIN AreaWarehouse a ON n.area_id = a.areaWarehouseCode
        INNER JOIN Warehouse w ON a.warehouseReference = w.warehouseCode
        ORDER BY n.createAT DESC
        """
    )
    fun getAllNotificationDTOs(): List<NotificationDTO>


}
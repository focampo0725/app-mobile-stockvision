package com.upc.stockvision.data.room.dao

import androidx.room.Dao
import androidx.room.Query
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


}
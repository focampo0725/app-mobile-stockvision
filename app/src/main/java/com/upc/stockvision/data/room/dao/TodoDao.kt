package com.upc.stockvision.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.upc.stockvision.domain.entities.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM Todo ")
    suspend fun getAllQupte():List<Todo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(todo:List<Todo>)

    @Query("DELETE FROM Todo")
    suspend fun deleteAllQuotes()
}


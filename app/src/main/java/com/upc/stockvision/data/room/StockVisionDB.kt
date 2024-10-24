package com.upc.stockvision.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.upc.stockvision.data.room.dao.TodoDao
import com.upc.stockvision.domain.entities.Todo

@Database(entities = [
    Todo::class
], version = 1)
abstract class StockVisionDB : RoomDatabase() {
    abstract fun todoDao() : TodoDao
}
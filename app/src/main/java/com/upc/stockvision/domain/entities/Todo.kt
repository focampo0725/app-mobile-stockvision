package com.upc.stockvision.domain.entities

import androidx.room.*
import com.upc.stockvision.data.room.converter.TimeConverter
import java.util.*


@Entity(tableName = Todo.TABLE_NAME)
@TypeConverters(TimeConverter::class)
class Todo(@ColumnInfo(name = "alias") var alias : String
){
    companion object {
        const val TABLE_NAME = "Todo"
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}
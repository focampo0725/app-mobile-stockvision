package com.upc.stockvision.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.upc.stockvision.data.room.converter.TimeConverter
import java.util.*


@Entity(tableName = Session.TABLE_NAME)
@TypeConverters(TimeConverter::class)
class Session(
    @ColumnInfo(name = "identityUser") var identityUser:  String,
    @ColumnInfo(name = "name") var name:  String,
    @ColumnInfo(name = "fhaterSurname") var fhaterSurname:  String,
    @ColumnInfo(name = "motherSurname") var motherSurname:  String,
    @ColumnInfo(name = "startDate") var startDate: Date,
    @ColumnInfo(name = "finalDate") var finalDate:  Date,

    ) {
    companion object {
        const val TABLE_NAME = "Session"
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

}
package com.upc.stockvision.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = IdentityUser.TABLE_NAME)
class IdentityUser(
    @ColumnInfo(name = "identityUser") var identityUser:  String,
    @ColumnInfo(name = "name") var name:  String,
    @ColumnInfo(name = "fhaterSurname") var fhaterSurname:  String,
    @ColumnInfo(name = "motherSurname") var motherSurname:  String,
    @ColumnInfo(name = "password") var password:  String
) {


    companion object {
        const val TABLE_NAME = "IdentityUser"
    }
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}
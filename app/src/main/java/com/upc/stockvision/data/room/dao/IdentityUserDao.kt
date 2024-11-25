package com.upc.stockvision.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.upc.stockvision.domain.entities.IdentityUser

@Dao
interface IdentityUserDao : BaseDao<IdentityUser> {

    @Query("SELECT * FROM identityUser WHERE identityUser = :identityUser AND password = :password LIMIT 1")
    fun validateUser(identityUser: String, password: String): IdentityUser?

    @Query("SELECT * FROM identityUser WHERE identityUser = :identityUser LIMIT 1")
    fun searchUser(identityUser: String): IdentityUser?
}
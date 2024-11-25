package com.upc.stockvision.data.room.dao

import androidx.room.Dao
import com.upc.stockvision.domain.entities.Session


@Dao
interface SignInDao : BaseDao<Session>{

}
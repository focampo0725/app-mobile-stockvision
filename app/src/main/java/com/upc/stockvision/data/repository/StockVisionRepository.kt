package com.upc.stockvision.data.repository

import com.upc.stockvision.data.network.ManagementApi
import com.upc.stockvision.data.spf.ISharedPreferences
import javax.inject.Inject

class StockVisionRepository @Inject constructor(
    val spf: ISharedPreferences,
    val managementApi: ManagementApi
) {


}
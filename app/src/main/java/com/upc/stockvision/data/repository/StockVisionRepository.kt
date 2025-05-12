package com.upc.stockvision.data.repository

import com.upc.stockvision.data.network.ManagementApi
import com.upc.stockvision.data.room.dao.*
import com.upc.stockvision.data.spf.ISharedPreferences
import javax.inject.Inject

class StockVisionRepository @Inject constructor(
    val spf: ISharedPreferences,
    val managementApi: ManagementApi,
    val identityUserDao: IdentityUserDao,
    val categoryDao: CategoryDao,
    val warehouseDao: WarehouseDao,
    val areaWarehouseDao: AreaWarehouseDao,
    val supplierDao: SupplierDao,
    val productsDao: ProductsDao,
    val reserveAreaDao : ReserveAreaDao,
    val productMovementDao: ProductMovementDao

) {


}
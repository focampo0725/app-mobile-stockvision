package com.upc.stockvision.data.room


import androidx.room.Database
import androidx.room.RoomDatabase
import com.upc.stockvision.data.room.dao.*
import com.upc.stockvision.domain.entities.*

@Database(entities = [
    Todo::class,
    Session::class,
    IdentityUser::class,
    Product::class,
    AreaWarehouse::class,
    Supplier::class,
    Warehouse::class,
    ReserveArea::class,
    Category::class,
    ProductMovement::class,
    Notifications::class,
    ProductStock::class
], version = 1)
abstract class StockVisionDB : RoomDatabase() {

    abstract fun todoDao() : TodoDao
    abstract fun productStockDao() : ProductStockDao
    abstract fun sessionDao() : SignInDao
    abstract fun identityUserDao() : IdentityUserDao
    abstract fun productDao() : ProductDao
    abstract fun areaWarehouseDao() : AreaWarehouseDao
    abstract fun warehouseDao() : WarehouseDao
    abstract fun supplierDao() : SupplierDao
    abstract fun reserveAreaDao() : ReserveAreaDao
    abstract fun categoryDao() : CategoryDao
    abstract fun productMovementDao() : ProductMovementDao
    abstract fun notificationsDao() : NotificationsDao

}
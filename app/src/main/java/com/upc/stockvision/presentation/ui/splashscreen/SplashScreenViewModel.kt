package com.upc.stockvision.presentation.ui.splashscreen

import android.annotation.SuppressLint
import android.app.Notification
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.lifecycle.MutableLiveData
import com.upc.stockvision.R
import com.upc.stockvision.data.repository.StockVisionRepository

import com.upc.stockvision.domain.entities.*
import com.upc.stockvision.infrastructure.extensions.LCEState
import com.upc.stockvision.infrastructure.extensions.doAsync
import com.upc.stockvision.presentation.BaseViewModel
import com.upc.stockvision.presentation.IViewModel
import com.upc.stockvision.presentation.ui.home.HomeActivity
import com.upc.stockvision.presentation.ui.home.HomeViewModel
import com.upc.stockvision.presentation.ui.sign_in.SignInActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

sealed class SplashScreenState {
    class LoadActivity(val cls: Class<*>) : SplashScreenState()

}

@HiltViewModel
class SplashScreenViewModel @Inject constructor(val stockVisionRepository: StockVisionRepository):
    BaseViewModel<LCEState<SplashScreenState>, SplashScreenState>(),IViewModel<SplashScreenState>{

    @SuppressLint("StaticFieldLeak")
    @Inject
    @ApplicationContext
    lateinit var context: Context
    fun loadScreen(){
        val toGoSignIn: () -> Unit = {
            super.postMessage(SplashScreenState.LoadActivity(SignInActivity::class.java))
        }

        if (!stockVisionRepository.spf.isAuthenticateUser) {
            toGoSignIn.invoke()
        }else{
            super.postMessage(SplashScreenState.LoadActivity(HomeActivity::class.java))
        }
    }



    fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun populateData(){


        val suppliersToInsert = listOf(
            Supplier(supplierName = "MOUNT.ASSY.E", supplierCode = 1001),
            Supplier(supplierName = "CHAIN.ROLLER.ANSI-35.5FT", supplierCode = 1002),
            Supplier(supplierName = "TERMINAL BLOCK", supplierCode = 1003),
            Supplier(supplierName = "FILTER.ELEC", supplierCode = 1004),
            Supplier(supplierName = "AIR SCND", supplierCode = 1005),
            Supplier(supplierName = "CORE.HOC(DMO)", supplierCode = 1006),
            Supplier(supplierName = "NUT.HEX.SLOT (DMO)", supplierCode = 1007),
            Supplier(supplierName = "ROLLER CHAIN (DMO)", supplierCode = 1008),
            Supplier(supplierName = "WIPER BLAD", supplierCode = 1009)
        )



        val listUser = listOf(
            IdentityUser("14785236", "Lucia", "Perez", "Mendoza", "123"),
            IdentityUser("63258741", "Carlos", "Gomez", "Sanchez", "321"),
            IdentityUser("12345678", "María", "Lopez", "Vargas", "159"),
            IdentityUser("87654321", "José", "Martínez", "Diaz", "951")
        )

        val categoriesToInsert = listOf(
            Category(categoryName = "Camisetas", categoryCode = 2001),
            Category(categoryName = "Pantalones", categoryCode = 2002),
            Category(categoryName = "Chaquetas", categoryCode = 2003),
            Category(categoryName = "Tops", categoryCode = 2004),
            Category(categoryName = "Zapatos", categoryCode = 2005),
            Category(categoryName = "Accesorios", categoryCode = 2006),
            Category(categoryName = "Conjuntos", categoryCode = 2007)
        )

        val warehousesToInsert = listOf(
            Warehouse(warehouseName = "Almacén Central", warehouseCode = "ALM-C001"),
            Warehouse(warehouseName = "Almacén Norte", warehouseCode = "ALM-N001"),
            Warehouse(warehouseName = "Almacén Sur", warehouseCode = "ALM-S001"),
            Warehouse(warehouseName = "Almacén Este", warehouseCode = "ALM-E001"),
            Warehouse(warehouseName = "Almacén Oeste", warehouseCode = "ALM-O001")
        )
        val areaWarehousesToInsert = listOf(
            AreaWarehouse(areaWarehouseName = "ZONA Camisetas A", areaWarehouseCode = "ZC-CAM-A", warehouseReference = "ALM-C001"),
            AreaWarehouse(areaWarehouseName = "ZONA Pantalones B", areaWarehouseCode = "ZC-PAN-B", warehouseReference = "ALM-C001"),
            AreaWarehouse(areaWarehouseName = "ZONA Chaquetas C", areaWarehouseCode = "ZC-CHA-C", warehouseReference = "ALM-C001"),
            AreaWarehouse(areaWarehouseName = "ZONA Zapatos A", areaWarehouseCode = "ZN-ZAP-A", warehouseReference = "ALM-N001"),
            AreaWarehouse(areaWarehouseName = "ZONA Accesorios B", areaWarehouseCode = "ZN-ACC-B", warehouseReference = "ALM-N001"),
            AreaWarehouse(areaWarehouseName = "ZONA Conjuntos A", areaWarehouseCode = "ZS-CONJ-A", warehouseReference = "ALM-S001"),
            AreaWarehouse(areaWarehouseName = "ZONA Camisetas B", areaWarehouseCode = "ZS-CAM-B", warehouseReference = "ALM-S001"),
            AreaWarehouse(areaWarehouseName = "ZONA Tops C", areaWarehouseCode = "ZS-TOPS-C", warehouseReference = "ALM-S001"),
            AreaWarehouse(areaWarehouseName = "ZONA Chaquetas D", areaWarehouseCode = "ZS-CHA-D", warehouseReference = "ALM-S001"),
            AreaWarehouse(areaWarehouseName = "ZONA Zapatos A", areaWarehouseCode = "ZE-ZAP-A", warehouseReference = "ALM-E001"),
            AreaWarehouse(areaWarehouseName = "ZONA Camisetas B", areaWarehouseCode = "ZE-CAM-B", warehouseReference = "ALM-E001"),
            AreaWarehouse(areaWarehouseName = "ZONA Accesorios A", areaWarehouseCode = "ZO-ACC-A", warehouseReference = "ALM-O001"),
            AreaWarehouse(areaWarehouseName = "ZONA Conjuntos B", areaWarehouseCode = "ZO-CONJ-B", warehouseReference = "ALM-O001"),
            AreaWarehouse(areaWarehouseName = "ZONA Pantalones C", areaWarehouseCode = "ZO-PAN-C", warehouseReference = "ALM-O001")
        )

//        Product("Circus Enterizo Short", "Conjuntos",10, "MOUNT.ASSY.E", "Almacén Central", "Zona Ropa Mujer", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.enterizo))),

        val productsToInsert = listOf(
            Product(productName = "Blusa manga farol satén", photo = "", categoryCode = "2004", supplierCode = "1001"),
            Product(productName = "Pantalón palazo mujer", photo = "", categoryCode = "2002", supplierCode = "1002"),
            Product(productName = "Short denim con bordado", photo = "", categoryCode = "2002", supplierCode = "1003"),
            Product(productName = "Polo manga corta stretch", photo = "", categoryCode = "2001", supplierCode = "1004"),
            Product(productName = "Falda acampanada de lino", photo = "", categoryCode = "2006", supplierCode = "1005"),
            Product(productName = "Casaca rompeviento impermeable", photo = "", categoryCode = "2003", supplierCode = "1006"),
            Product(productName = "Ropa de bebé enterizo polar", photo = "", categoryCode = "2007", supplierCode = "1007"),
            Product(productName = "Casaca jean oversize", photo = "", categoryCode = "2003", supplierCode = "1008"),
            Product(productName = "Short deportivo de licra", photo = "", categoryCode = "2002", supplierCode = "1009"),
            Product(productName = "Falda midi plisada", photo = "", categoryCode = "2006", supplierCode = "1003"),
            Product(productName = "Ropa de bebé con gorro", photo = "", categoryCode = "2007", supplierCode = "1001")
        )

        val productStockToInsert = listOf(
            ProductStock(productId = 1, areaCode = "ZC-CAM-A", stock = 40),
            ProductStock(productId = 2, areaCode = "ZO-PAN-C", stock = 35),
            ProductStock(productId = 3, areaCode = "ZE-ZAP-A", stock = 15),
            ProductStock(productId = 4, areaCode = "ZN-ZAP-A", stock = 50),
            ProductStock(productId = 5, areaCode = "ZN-ACC-B", stock = 30),
            ProductStock(productId = 6, areaCode = "ZN-ZAP-A", stock = 25),
            ProductStock(productId = 7, areaCode = "ZS-TOPS-C", stock = 12),
            ProductStock(productId = 8, areaCode = "ZC-CHA-C", stock = 18),
            ProductStock(productId = 9, areaCode = "ZE-CAM-B", stock = 22),
            ProductStock(productId = 10, areaCode = "ZO-CONJ-B", stock = 28),
            ProductStock(productId = 11, areaCode = "ZS-CHA-D", stock = 14)
        )




        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val specificDate: Date = dateFormat.parse("07/06/2025")

        val listNotifications = listOf(
            Notifications(0, "Producto Entrante", "Camiseta Deportiva", "Almacén Central", "Zona Ropa Mujer", 15,specificDate),
            Notifications(1, "Reserva", "Camiseta Deportiva", "Almacén Central", "Zona Ropa Mujer", 15,specificDate),
            Notifications(2, "Stock Bajo", "Chaqueta Bomber", "Almacén Norte", "Zona Ropa Hombre", 4, specificDate),
            Notifications(2, "Stock Bajo", "Cazadora de Cuero", "Almacén Central", "Zona Ropa Mujer", 2,specificDate),
            Notifications(2, "Stock Bajo", "Pantalón Casual", "Almacén Central", "Zona Ropa Mujer", 2,specificDate)
        )



        doAsync{
            stockVisionRepository.supplierDao.deleteAll()
            stockVisionRepository.productsDao.deleteAll()
            stockVisionRepository.identityUserDao.deleteAll()
            stockVisionRepository.categoryDao.deleteAll()
            stockVisionRepository.warehouseDao.deleteAll()
            stockVisionRepository.areaWarehouseDao.deleteAll()
            stockVisionRepository.notificationsDao.deleteAllSequence()
            stockVisionRepository.notificationsDao.deleteAll()
            stockVisionRepository.productStockDao.deleteAll()

            stockVisionRepository.supplierDao.insertAll(suppliersToInsert)
            stockVisionRepository.productsDao.insertAll(productsToInsert)
            stockVisionRepository.identityUserDao.insertAll(listUser)
            stockVisionRepository.categoryDao.insertAll(categoriesToInsert)
            stockVisionRepository.warehouseDao.insertAll(warehousesToInsert)
            stockVisionRepository.areaWarehouseDao.insertAll(areaWarehousesToInsert)
            stockVisionRepository.notificationsDao.insertAll(listNotifications)
            stockVisionRepository.productStockDao.insertAll(productStockToInsert)
        }
    }


    override val renderState: MutableLiveData<LCEState<SplashScreenState>>
        get() = getLiveData()

}
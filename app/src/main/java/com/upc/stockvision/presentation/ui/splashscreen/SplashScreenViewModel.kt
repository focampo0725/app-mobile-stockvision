package com.upc.stockvision.presentation.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.lifecycle.MutableLiveData
import com.upc.stockvision.R
import com.upc.stockvision.data.repository.StockVisionRepository
import com.upc.stockvision.domain.dto.IdentityUserDTO
import com.upc.stockvision.domain.dto.IdentityUserPopulateDTO
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

        val listSupplier = listOf(
            Supplier("MOUNT.ASSY.E", 101),
            Supplier("CHAIN.ROLLER.ANSI-35.5FT", 102),
            Supplier("TERMINAL BLOCK", 103),
            Supplier("FILTER.ELEC", 104),
            Supplier("MOUNT.ASSY.E", 105),
            Supplier("AIR SCND", 106),
            Supplier("CORE.HOC(DMO)", 107),
            Supplier("NUT.HEX.SLOT (DMO)", 108),
            Supplier("ROLLER CHAIN (DMO)", 109),
            Supplier("WIPER BLADE", 110)

        )

        val listUser = listOf(
            IdentityUser("14785236", "Lucia", "Perez", "Mendoza", "123"),
            IdentityUser("63258741", "Carlos", "Gomez", "Sanchez", "321"),
            IdentityUser("12345678", "María", "Lopez", "Vargas", "159"),
            IdentityUser("87654321", "José", "Martínez", "Diaz", "951")
        )

        val listCategory = listOf(
            Category("Camisetas", 1),
            Category("Pantalones", 2),
            Category("Chaquetas", 3),
            Category("Tops", 3),
            Category("Zapatos", 4),
            Category("Accesorios", 6),
            Category("Conjuntos", 6)
        )

        val listWarehouse = listOf(
            Warehouse("Almacén Central", 1),
            Warehouse("Almacén Norte", 2),
            Warehouse("Almacén Sur", 3),
            Warehouse("Almacén Este", 4),
            Warehouse("Almacén Oeste", 5)
        )

        val listAreaWarehouse = listOf(
            AreaWarehouse("Zona Ropa Hombre", 21, 1),
            AreaWarehouse("Zona Ropa Mujer", 22, 1),
            AreaWarehouse("Zona Ropa Infantil", 23, 2),
            AreaWarehouse("Zona Calzado", 24, 3),
            AreaWarehouse("Zona Accesorios", 25, 4),
            AreaWarehouse("Zona Ropa Deportiva", 26, 5)
        )

        val listProducts = listOf(
            Products("Circus Enterizo Short", "Conjuntos",10, "MOUNT.ASSY.E", "Almacén Central", "Zona Ropa Mujer", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.enterizo))),
            Products("Dos piezas", "Chaquetas",50, "CHAIN.ROLLER.ANSI-35.5FT", "Almacén Central", "Zona Ropa Mujer", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.conjunto_pieza))),
            Products("SHEIN Tie Back", "Conjuntos",10, "WIPER BLADE", "Almacén Central", "Zona Ropa Mujer", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.tie_back))),

            Products("Striped Cropped", "Tops",40, "WIPER BLADE", "Almacén Norte", "Zona Ropa Mujer", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.cropp))),
            Products("Cross Wrap Solid", "Tops",20, "WIPER BLADE", "Almacén Norte", "Zona Ropa Mujer", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.solid))),
            Products("Top rayas negras", "Tops",50, "WIPER BLADE", "Almacén Norte", "Zona Ropa Mujer", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.rayas_negras))),
            Products("Camiseta Básica", "Camisetas", 10, "MOUNT.ASSY.E", "Almacén Central", "Zona Ropa Mujer", "imagen1_base64"),
            Products("Camiseta Deportiva", "Camisetas", 15, "CHAIN.ROLLER.ANSI-35.5FT", "Almacén Central", "Zona Ropa Mujer", "imagen2_base64"),
            Products("Camiseta Cuello V", "Camisetas", 12, "TERMINAL BLOCK", "Almacén Central", "Zona Ropa Mujer", "imagen3_base64"),

            //
            Products("Camiseta Básica", "Camisetas", 10, "MOUNT.ASSY.E", "Almacén Central", "Zona Ropa Mujer", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.camiseta_basica))),
            Products("Camiseta Deportiva", "Camisetas", 15, "CHAIN.ROLLER.ANSI-35.5FT", "Almacén Central", "Zona Ropa Mujer", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.camiseta_deportiva))),
            Products("Camiseta Cuello V", "Camisetas", 12, "TERMINAL BLOCK", "Almacén Central", "Zona Ropa Mujer", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.camiseta_v))),

            // Categoría: Pantalones
            Products("Pantalón Casual", "Pantalones", 18, "MOUNT.ASSY.E", "Almacén Central", "Zona Ropa Mujer", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.pantalon_casual))),
            Products("Pantalón Chino", "Pantalones", 22, "AIR SCND", "Almacén Central", "Zona Ropa Mujer", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.pantalon_chino))),
            Products("Jeans Ajustados", "Pantalones", 20, "CORE.HOC(DMO)", "Almacén Central", "Zona Ropa Mujer", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.ajustados))),

            // Categoría: Chaquetas
            Products("Chaqueta Estilo Urbano", "Chaquetas", 10, "FILTER.ELEC", "Almacén Central", "Zona Ropa Mujer", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.chaqueta_urbano))),
            Products("Abrigo Invierno", "Chaquetas", 14, "NUT.HEX.SLOT (DMO)", "Almacén Central", "Zona Ropa Mujer", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.abrigo_mujer_inv))),
            Products("Cazadora de Cuero", "Chaquetas", 8, "ROLLER CHAIN (DMO)", "Almacén Central", "Zona Ropa Mujer", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.cazadora_mujer))),

            // Almacén Norte
            // Categoría: Camisetas
            Products("Camiseta Casual", "Camisetas", 30, "WIPER BLADE", "Almacén Norte", "Zona Ropa Hombre", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.camiseta_casual))),
            Products("Camiseta Deportiva", "Camisetas", 25, "FILTER.ELEC", "Almacén Norte", "Zona Ropa Hombre", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.camiseta_deportiva_ho))),
            Products("Camiseta Manga Larga", "Camisetas", 10, "MOUNT.ASSY.E", "Almacén Norte", "Zona Ropa Hombre", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.camiseta_larga_h))),

            // Categoría: Pantalones
            Products("Pantalón Cargo", "Pantalones", 35, "CHAIN.ROLLER.ANSI-35.5FT", "Almacén Norte", "Zona Ropa Hombre", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.p_cargo_nor))),
            Products("Pantalón Deportivo", "Pantalones", 40, "TERMINAL BLOCK", "Almacén Norte", "Zona Ropa Hombre", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.deportivo_h_no))),
            Products("Pantalón Formal", "Pantalones", 22, "AIR SCND", "Almacén Norte", "Zona Ropa Hombre", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.formal_h_no))),

            // Categoría: Chaquetas
            Products("Chaqueta Bomber", "Chaquetas", 5, "FILTER.ELEC", "Almacén Norte", "Zona Ropa Hombre", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.bomber_h_no))),
            Products("Chaqueta Pluma", "Chaquetas", 10, "NUT.HEX.SLOT (DMO)", "Almacén Norte", "Zona Ropa Hombre", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.pluma_h_no))),
            Products("Chaleco Outdoor", "Chaquetas", 8, "ROLLER CHAIN (DMO)", "Almacén Norte", "Zona Ropa Hombre", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.outdoor_h_no))),

            // Almacén Sur
            // Categoría: Camisetas
            Products("Camiseta Estampada", "Camisetas", 40, "WIPER BLADE", "Almacén Sur", "Zona Ropa Infantil", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.camiseta_sur_in))),
            Products("Camiseta Básica", "Camisetas", 35, "FILTER.ELEC", "Almacén Sur", "Zona Ropa Infantil", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.basica_sur_in))),
            Products("Camiseta de Cuello Alto", "Camisetas", 12, "MOUNT.ASSY.E", "Almacén Sur", "Zona Ropa Infantil", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.alto_sur_in))),

            // Categoría: Pantalones
            Products("Pantalón Corto", "Pantalones", 50, "CHAIN.ROLLER.ANSI-35.5FT", "Almacén Sur", "Zona Ropa Infantil", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.corto_sur_in))),
            Products("Pantalón Estilo Jogger", "Pantalones", 60, "TERMINAL BLOCK", "Almacén Sur", "Zona Ropa Infantil", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.jogger_sur_in))),



            // Almacén Este
            // Categoría: Camisetas
//            Products("Camiseta de Algodón", "Camisetas", 20, "WIPER BLADE", "Almacén Este", "Zona Calzado", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.cropp))),
//            Products("Camiseta sin Mangas", "Camisetas", 25, "FILTER.ELEC", "Almacén Este", "Zona Calzado", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.cropp))),
//            Products("Camiseta Ajustada", "Camisetas", 15, "MOUNT.ASSY.E", "Almacén Este", "Zona Calzado", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.cropp))),
//
//            // Categoría: Pantalones
//            Products("Pantalón Recto", "Pantalones", 18, "CHAIN.ROLLER.ANSI-35.5FT", "Almacén Este", "Zona Calzado", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.cropp))),
//            Products("Pantalón de Vestir", "Pantalones", 30, "TERMINAL BLOCK", "Almacén Este", "Zona Calzado", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.cropp))),
//            Products("Jeans Rasgados", "Pantalones", 22, "AIR SCND", "Almacén Este", "Zona Calzado", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.cropp))),
//
//            // Categoría: Chaquetas
//            Products("Chaqueta de Lana", "Chaquetas", 12, "CORE.HOC(DMO)", "Almacén Este", "Zona Calzado", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.cropp))),
//            Products("Chaqueta Casual", "Chaquetas", 18, "NUT.HEX.SLOT (DMO)", "Almacén Este", "Zona Calzado", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.cropp))),
//            Products("Abrigo Corto", "Chaquetas", 10, "ROLLER CHAIN (DMO)", "Almacén Este", "Zona Calzado", bitmapToBase64(BitmapFactory.decodeResource(context.resources, R.drawable.cropp)))

        )




        doAsync{
            stockVisionRepository.supplierDao.deleteAll()
            stockVisionRepository.productsDao.deleteAll()
            stockVisionRepository.identityUserDao.deleteAll()
            stockVisionRepository.categoryDao.deleteAll()
            stockVisionRepository.warehouseDao.deleteAll()
            stockVisionRepository.areaWarehouseDao.deleteAll()

            stockVisionRepository.supplierDao.insertAll(listSupplier)
            stockVisionRepository.productsDao.insertAll(listProducts)
            stockVisionRepository.identityUserDao.insertAll(listUser)
            stockVisionRepository.categoryDao.insertAll(listCategory)
            stockVisionRepository.warehouseDao.insertAll(listWarehouse)
            stockVisionRepository.areaWarehouseDao.insertAll(listAreaWarehouse)
        }
    }


    override val renderState: MutableLiveData<LCEState<SplashScreenState>>
        get() = getLiveData()

}
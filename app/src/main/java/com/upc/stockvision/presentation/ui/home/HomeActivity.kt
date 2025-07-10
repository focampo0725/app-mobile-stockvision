package com.upc.stockvision.presentation.ui.home

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.upc.stockvision.R
import com.upc.stockvision.databinding.ActivityHomeBinding
import com.upc.stockvision.databinding.FragmentIncomingProductBinding
import com.upc.stockvision.infrastructure.AppState
import com.upc.stockvision.infrastructure.extensions.showCustomToast
import com.upc.stockvision.infrastructure.utils.Constants
import com.upc.stockvision.presentation.BaseActivity
import com.upc.stockvision.presentation.ui.create_product_movement.CreateProductMovementFragment
import com.upc.stockvision.presentation.ui.detail_product.DetailProductFragment
import com.upc.stockvision.presentation.ui.detail_reserve_space.DetailReserveFragment
import com.upc.stockvision.presentation.ui.home_presentation.HomePresentationFragment
import com.upc.stockvision.presentation.ui.incoming_product.IncomingProductFragment
import com.upc.stockvision.presentation.ui.inventory_control.InventoryControlFragment
import com.upc.stockvision.presentation.ui.notifications.NotificationsFragment
import com.upc.stockvision.presentation.ui.product_movement_record.ProductMovementRecordFragment
import com.upc.stockvision.presentation.ui.product_registration.ProductRegistrationFragment
import com.upc.stockvision.presentation.ui.reserve_space.ReserveSpaceFragment
import com.upc.stockvision.presentation.ui.show_reservation.ShowReservationFragment
import com.upc.stockvision.presentation.ui.sign_in.SignInActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity<HomeViewModel,HomeSatate>() {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: ActivityHomeBinding

    @Inject
    lateinit var productRegistrationFragment: ProductRegistrationFragment
    @Inject
    lateinit var incomingProductFragment: IncomingProductFragment
    @Inject
    lateinit var inventoryControlFragment: InventoryControlFragment
    @Inject
    lateinit var reserveSpaceFragment: ReserveSpaceFragment
    @Inject
    lateinit var homePresentationFragment: HomePresentationFragment
    @Inject
    lateinit var detailProductFragment: DetailProductFragment
    @Inject
    lateinit var showReservationFragment: ShowReservationFragment
    @Inject
    lateinit var detailReserveFragment: DetailReserveFragment
    @Inject
    lateinit var createProductMovementFragment: CreateProductMovementFragment
    @Inject
    lateinit var productMovementRecordFragment: ProductMovementRecordFragment
    @Inject
    lateinit var detailProductMovementRecordFragment: ProductMovementRecordFragment
    @Inject
    lateinit var notificationsFragment: NotificationsFragment
    @Inject
    lateinit var appState: AppState


    override fun processRenderState(renderState: HomeSatate, context: Context) {
        TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel(viewModel)
        immersiveScreen(window)
        setSupportActionBar(binding.toolbar)
        closeApp()
        getSupportActionBar()?.setDisplayShowTitleEnabled(false);
        supportFragmentManager.beginTransaction()
            .replace(R.id.content_frame, homePresentationFragment)
            .addToBackStack(null)
            .commit()
        // Configurar el DrawerLayout y NavigationView
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navigationView: NavigationView = binding.navView
        loadNotification()
        drawProductDetail()
        drawProductMovementDetail()
        drawInventoryControlFromDetail()
        drawCreateReserveArea()
        drawCreateMovement()
        drawDetailovement()
        DrawShowReserve()
        drawReserveDetail()
        navigationView.setNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.nav_item1 -> {
//                    removeCurrentFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.content_frame, homePresentationFragment)
                        .addToBackStack(null)
                        .commit()
                }
                R.id.nav_item2 -> {
                    updateTitle(item.title.toString())
                    removeCurrentFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.content_frame, productRegistrationFragment)
                        .addToBackStack(null)
                        .commit()
                }
                R.id.nav_item3 -> {
                    removeCurrentFragment()
                    updateTitle(item.title.toString())
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.content_frame, incomingProductFragment)
                        .addToBackStack(null)
                        .commit()
                }
                R.id.nav_item4 -> {
                    removeCurrentFragment()
                    updateTitle(item.title.toString())
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.content_frame, showReservationFragment)
                        .addToBackStack(null)
                        .commit()
                }
                R.id.nav_item5 -> {
                    removeCurrentFragment()
                    updateTitle(item.title.toString())
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.content_frame, inventoryControlFragment)
                        .addToBackStack(null)
                        .commit()
                }
                R.id.nav_item6 -> {
                    removeCurrentFragment()
                    updateTitle(item.title.toString())
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.content_frame, productMovementRecordFragment)
                        .addToBackStack(null)
                        .commit()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }


        // Configurar el icono de menú en la Toolbar
        binding.toolbar.setNavigationIcon(R.drawable.ic_menu_24) // Asegúrate de tener este ícono en drawable
        binding.toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START) // Abre el Navigation Drawer
        }

    }

    fun loadNotification(){
        val fragmentToShow = intent.getStringExtra("fragment_to_show")

        if (fragmentToShow == "0") {
            // Mostrar el Fragmento 1
            supportFragmentManager.beginTransaction()
                .replace(R.id.content_frame, incomingProductFragment)  // Aquí se reemplaza por el Fragmento 1
                .commit()
        } else if (fragmentToShow == "1") {
            // Mostrar el Fragmento 2
            supportFragmentManager.beginTransaction()
                .replace(R.id.content_frame, showReservationFragment)  // Aquí se reemplaza por el Fragmento 2
                .commit()
        }
    }
    // Inflar el menú de la Toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu) // Asegúrate de que esto sea correcto
        return true
    }

    // Manejar clics en los ítems del menú de la Toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.action_notification -> {
                replaceFragment(notificationsFragment,null)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun removeCurrentFragment() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.content_frame)
        currentFragment?.let {
            supportFragmentManager.beginTransaction()
                .remove(it)
                .commit()
        }
    }
    private fun replaceFragment(fragment: Fragment, args: Bundle? = null) {
        removeCurrentFragment()
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        fragment.arguments = args
        transaction.replace(R.id.content_frame, fragment)
            .addToBackStack(null)
        transaction.commit()
    }

    private fun replaceFragmentArea(fragment: Fragment, args: Bundle? = null) {
        removeCurrentFragment()
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        fragment.arguments = args
        transaction.replace(R.id.content_frame, fragment)
            .addToBackStack(null)
        transaction.commit()
    }

    fun drawProductDetail() {
        appState.onDrawProductDetail = {
            val args = Bundle().apply {
                putSerializable(Constants.PRODUCT_KEY, it)
            }
            replaceFragment(detailProductFragment, args)
        }

    }
    fun drawProductMovementDetail() {
        appState.onDrawProductMovementDetail = {
            val args = Bundle().apply {
                putSerializable(Constants.PRODUCT_MOVEMENT_KEY, it)
            }
            replaceFragment(detailProductMovementRecordFragment, args)
        }

    }

    fun drawReserveDetail() {
        appState.onDrawReserveDetail = {
            val args = Bundle().apply {
                putSerializable(Constants.RESERVE_KEY, it)
            }
            replaceFragment(detailReserveFragment, args)
        }

    }

    fun drawInventoryControlFromDetail() {
        appState.onDrawinventoryControlFragment = {
            replaceFragment(inventoryControlFragment, null)
        }

    }

    fun drawCreateReserveArea() {
        appState.onDrawCreateReserveArea = {
            replaceFragment(reserveSpaceFragment, null)
        }

    }

    fun DrawShowReserve() {
        appState.onDrawShowReserve = {
            replaceFragment(showReservationFragment, null)
        }

    }



    fun drawCreateMovement() {
        appState.onDrawCreateMovement = {
            replaceFragment(createProductMovementFragment, null)
        }

    }

    fun drawDetailovement() {
        appState.onDrawMovement = {
            replaceFragment(productMovementRecordFragment, null)
        }

    }



    private fun closeApp(){
        binding.cvLogout.setOnClickListener {
            onNextActivity(SignInActivity::class.java,null,true)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }
    }

    private fun updateTitle(newTitle: String) {
        binding.toolbarTitle.text = newTitle
    }
}

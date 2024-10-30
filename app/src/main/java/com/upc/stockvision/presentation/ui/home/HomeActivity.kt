package com.upc.stockvision.presentation.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.upc.stockvision.R
import com.upc.stockvision.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar la Toolbar
        setSupportActionBar(binding.toolbar)
        closeApp()

        // Configurar el DrawerLayout y NavigationView
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navigationView: NavigationView = binding.navView

        // Manejar clics en el NavigationView
        navigationView.setNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.nav_item1 -> {
                    updateTitle(item.title.toString())
                    Toast.makeText(this, "Seleccionaste Item 1", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_item2 -> {
                    updateTitle(item.title.toString())
                    Toast.makeText(this, "Seleccionaste Item 2", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_item3 -> {
                    updateTitle(item.title.toString())
                    Toast.makeText(this, "Seleccionaste Item 3", Toast.LENGTH_SHORT).show()
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

    // Inflar el menú de la Toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu) // Asegúrate de que esto sea correcto
        return true
    }

    // Manejar clics en los ítems del menú de la Toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.action_notification -> {
                // Acción al hacer clic en la campana
                Toast.makeText(this, "Campana clickeada", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun closeApp(){
        binding.cvLogout.setOnClickListener {
            Toast.makeText(this, "LogOut", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateTitle(newTitle: String) {
        binding.toolbarTitle.text = newTitle
    }
}

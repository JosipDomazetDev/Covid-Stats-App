package com.covidstats.covidstats

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.covidstats.sovid.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.content.*


class MainActivity : AppCompatActivity() {


    private val navController by lazy {
        Navigation.findNavController(
            this,
            R.id.nav_host_fragment
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavigation()
    }


    private val appBarConfiguration: AppBarConfiguration by lazy {
        AppBarConfiguration(navController.graph, drawerLayout)
    }

    private fun setupNavigation() {
        setSupportActionBar(toolbar)

        bottom_nav.setupWithNavController(navController)
        navView.setupWithNavController(navController)

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.detailFragment || destination.id == R.id.appInfoFragment || destination.id == R.id.numbersInfoFragment) {
                bottom_nav.visibility = View.GONE
            } else {
                bottom_nav.visibility = View.VISIBLE
            }
        }

    }


    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            navController,
            appBarConfiguration
        ) || super.onSupportNavigateUp()
    }


    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.dots, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        return NavigationUI.onNavDestinationSelected(
            item,
            navController
        ) || super.onOptionsItemSelected(item)
    }

}

package com.chilltouch.fieldtracker

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.location.LocationManager
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var locationManager : LocationManager? = null
    var locationListener : CordinateListener? = null

    var drawer : DrawerLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.createMenus(savedInstanceState)

        if (locationListener == null) {
            locationListener = CordinateListener()
        }

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) +
                    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)) != PackageManager.PERMISSION_GRANTED) {
        } else {
            var distance = getSavedData(DataStoreConstants.DISTANCE_TO_UPDATE_IN_METERS_KEY, DataStoreConstants.DISTANCE_TO_UPDATE_IN_METERS_STR).toFloat()
            var time = getSavedData(DataStoreConstants.TIME_TO_UPDATE_IN_SEC_KEY, DataStoreConstants.DISTANCE_TO_UPDATE_IN_METERS_STR).toLong()

            locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, time, distance, locationListener)
        }
    }

    private fun createMenus(savedInstanceState: Bundle?) {
        var toolbar : Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawer = findViewById<DrawerLayout?>(R.id.activity_main)

        var navigationView : NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        var toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer?.addDrawerListener(toggle)

        toggle.syncState()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()
            navigationView.setCheckedItem(R.id.fragment_container)
        }
    }

    override fun onBackPressed() {
        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
            drawer?.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.nav_home) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment())
                .addToBackStack(null).commit()
        } else if (item.itemId == R.id.nav_settings) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, SettingsFragment())
                .addToBackStack(null).commit()
        }

        drawer?.closeDrawer(GravityCompat.START)
        return true
    }

    fun saveData(key: String, value: String){
        var sharedPreferences = getSharedPreferences(DataStoreConstants.SHARED_PREFS_KEY, Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = sharedPreferences.edit()

        editor.putString(key, value)
        editor.apply()
    }

    fun getSavedData(key: String, default: String): String {
        var sharedPreferences = getSharedPreferences(DataStoreConstants.SHARED_PREFS_KEY, Context.MODE_PRIVATE)

        var result = sharedPreferences.getString(key, default)

        return result
    }

}

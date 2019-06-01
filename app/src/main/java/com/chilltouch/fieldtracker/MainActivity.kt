package com.chilltouch.fieldtracker

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.location.LocationManager
import android.support.v4.content.ContextCompat

class MainActivity : AppCompatActivity() {

    var locationManager : LocationManager? = null
    var locationListener : CordinateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (locationListener == null) {
            locationListener = CordinateListener()
        }

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) +
                    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)) != PackageManager.PERMISSION_GRANTED) {
        } else {
            locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60, 500f, locationListener)
        }
    }
}

package com.chilltouch.fieldtracker

import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.util.Log

class CoordinateListener : LocationListener {

    private val TAG = CoordinateListener::class.qualifiedName

    override fun onLocationChanged(loc: Location?) {
        var longtitude = loc?.longitude
        var latitude = loc?.latitude

        Log.v(TAG, "latitude: " + latitude + ", longtitude: " + longtitude)
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
    }

    override fun onProviderEnabled(p0: String?) {
    }

    override fun onProviderDisabled(p0: String?) {
    }
}
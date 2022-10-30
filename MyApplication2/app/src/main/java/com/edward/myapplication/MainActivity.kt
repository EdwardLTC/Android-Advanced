package com.edward.myapplication

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    999
                )
            } else {
                it.isMyLocationEnabled = true
            }
            it.addMarker(
                MarkerOptions().title("hahahaah")
                    .position(LatLng(10.8529391, 106.6295448))
            )
            it.isTrafficEnabled = true
            it.isBuildingsEnabled = true
            it.isIndoorEnabled = true
            it.mapType = GoogleMap.MAP_TYPE_SATELLITE
            it.uiSettings.isMyLocationButtonEnabled = true
            it.uiSettings.isZoomControlsEnabled = true
            it.uiSettings.isRotateGesturesEnabled = true
            it.uiSettings.isCompassEnabled = true
            it.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(10.8529391, 106.6295448),
                    18F
                )
            )
            it.uiSettings.setAllGesturesEnabled(true)
        }
    }
}



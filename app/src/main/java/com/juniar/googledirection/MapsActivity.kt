package com.juniar.googledirection

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,MapsView {

    private lateinit var mMap: GoogleMap
    lateinit var presenter: MapsPresenter
    lateinit var polyline: Polyline

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        presenter= MapsPresenter(this)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val from=LatLng(-7.731291, 110.383098)
        val to=LatLng(-7.777307, 110.417006)
        presenter.getDirection(from,to)
        mMap.addMarker(MarkerOptions().position(from).title("From"))
        mMap.addMarker(MarkerOptions().position(to).title("To"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(from, 16.0f))
    }

    override fun onGetDirection(error: Boolean, direction: DirectionResponse?, t: Throwable?) {
        direction?.let {
            it.routes.forEachIndexed { index, route ->
                var distance=route.legs[index].distance.text
                var time=route.legs[index].duration.text

                var polylineEncoded=it.routes[0].overviewPolyline.points
                var listPolyline= decodePoly(polylineEncoded)
                polyline=mMap.addPolyline(PolylineOptions()
                        .addAll(listPolyline)
                        .width(10f)
                        .color(R.color.polyline_color)
                        .geodesic(true))

                Toast.makeText(applicationContext, "Distance:$distance, Duration:$time", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroyPresenter()
    }
}

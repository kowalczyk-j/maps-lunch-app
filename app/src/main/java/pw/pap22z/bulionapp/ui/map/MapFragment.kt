package pw.pap22z.bulionapp.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.AsyncTask

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*


import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.data.RestaurantDatabase
import pw.pap22z.bulionapp.data.entities.Restaurant
import pw.pap22z.bulionapp.databinding.FragmentMapBinding


class MapFragment : Fragment(), OnMapReadyCallback {
    private var map: GoogleMap? = null
    private var cameraPosition: CameraPosition? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val defaultLocation = LatLng(52.225665764, 21.003833318)
    private var locationPermissionGranted = false
    private var lastKnownLocation: Location? = null
    private var _binding: FragmentMapBinding? = null
    private lateinit var mapViewModel: MapViewModel

    private val binding get() = _binding!!
    private var restaurants: List<Restaurant> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        getLocationPermission()
        updateLocationUI()
        getDeviceLocation()
        Log.d("MyTag", "zaczynam")

        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION)
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION)
        }

        Log.d("MyTag", "zaczynam")

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment

        mapViewModel = ViewModelProvider(this).get(MapViewModel::class.java)

        /*
            mapViewModel.allRestaurants.observe(viewLifecycleOwner) {
                restaurants = it
                updateMap()
            } */

        mapFragment.getMapAsync(this)

        return root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        map?.let { map ->
            outState.putParcelable(KEY_CAMERA_POSITION, map.cameraPosition)
            outState.putParcelable(KEY_LOCATION, lastKnownLocation)
        }
        super.onSaveInstanceState(outState)
    }
    /*
        private fun getCoordinates(address: String): LatLng? {
            val geocoder = context?.let { Geocoder(it) }
            val likeAddress = geocoder?.getFromLocationName(address + "Warszawa, Polska", 1)
            if (likeAddress != null && likeAddress.isNotEmpty()) {
                val lat = likeAddress[0].latitude
                val lng = likeAddress[0].longitude
                return LatLng(lat, lng)
            } else {
                // Handle the case where no results are found
                Log.d("GEOCODER", "No results found for address: $address")
                return null
            }
        }
    */
    private fun updateMap() {
        /*
        for (restaurant in restaurants) {
            val latLng = getCoordinates(restaurant.address)
            latLng?.let { MarkerOptions().position(it).title("Marker in Warsaw") }
                ?.let { map?.addMarker(it) } */
        /*
        for (restaurant in restaurants) {
            Log.d("MyTag", "iteruje po mapkach!!!!!!!!!!!!!!!!!!!!!!!!!!")
            Log.d("Location", (restaurant.latitude).toString() + (restaurant.longitude).toString())
            map?.let { addMarkerToMap(it, restaurant.latitude, restaurant.longitude, restaurant.name) }
            }
        */
        map?.clear()
        for (restaurant in restaurants) {
            addRestaurantToMap(restaurant)
        }

    }

    private fun addRestaurantToMap (restaurant: Restaurant){
        val snippetInfo =
                restaurant.price.toString() +
                restaurant.rating.toString() +
                restaurant.hour_start.toString() + '-' +
                restaurant.hour_end.toString()

        val markerOptions = MarkerOptions().position(LatLng(restaurant.latitude, restaurant.longitude)).
        title(restaurant.name).snippet(snippetInfo)
        map?.addMarker(markerOptions)
    }


    private fun addMarkerToMap(map: GoogleMap, latitude: Double, longitude: Double, title: String) {
        val markerOptions = MarkerOptions().position(LatLng(latitude, longitude)).title(title)
        map.addMarker(markerOptions)
    }


    override fun onMapReady(map: GoogleMap) {

        this.map = map

        val infoWindowAdapter = object : GoogleMap.InfoWindowAdapter {
            override fun getInfoWindow(p0: Marker): View? {
                return null
            }

            override fun getInfoContents(marker: Marker): View {
                // Inflate the layouts for the info window, title and snippet.
                val inflater = LayoutInflater.from(context)
                val view = inflater.inflate(R.layout.custom_info_window, null)
                val textViewTitle = view.findViewById<TextView>(R.id.titly)
                textViewTitle.text = marker?.title

                val textViewSnippet = view.findViewById<TextView>(R.id.snippet)
                textViewSnippet.text = marker?.snippet

                val infoButton = view.findViewById<Button>(R.id.button_more)
                infoButton.text = "Show more"
                infoButton.setOnClickListener {
                    //Perform an action when button is clicked
                }

                val routeButton = view.findViewById<Button>(R.id.route_button)
                routeButton.setOnClickListener {
                    // use the DirectionsApi class to request a route
                    // from the user's current location to the clicked marker
                }

                return view
            }
        }

            this.map!!.setInfoWindowAdapter(infoWindowAdapter)

            /*val inflater = LayoutInflater.from(context)
                val view = inflater.inflate(R.layout.custom_info_window, null)

                val infoWindow = layoutInflater.inflate(R.layout.custom_info_window,
                    view.findViewById<FrameLayout>(R.id.map_fragment), false)

                val title = infoWindow.findViewById<TextView>(R.id.titly)
                title.text = marker.title
                val snippet = infoWindow.findViewById<TextView>(R.id.snippet)
                snippet.text = marker.snippet
                Log.d("MyTag", "POD KONIEC GET INFO CONTENTS")
                return infoWindow
            }
        })*/

        //map.setInfoWindowAdapter(infoWindowAdapter)

        mapViewModel.allRestaurants.observe(viewLifecycleOwner) {
            restaurants = it
            //mapViewModel.updateRestaurantCoordinates()
            updateMap()
        }
        addMarkerToMap(map,52.237049,21.017532, "Warsaw")

        getLocationPermission()
        updateLocationUI()
        getDeviceLocation()

    }

    private fun getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            map?.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                LatLng(lastKnownLocation!!.latitude,
                                    lastKnownLocation!!.longitude), DEFAULT_ZOOM.toFloat()))
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.")
                        Log.e(TAG, "Exception: %s", task.exception)
                        map?.moveCamera(CameraUpdateFactory
                            .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat()))
                        map?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }
    private fun getLocationPermission() {
        val nowThis = requireActivity()
        if (ContextCompat.checkSelfPermission(nowThis.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(nowThis, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
        updateLocationUI()
    }
    @SuppressLint("MissingPermission")
    private fun updateLocationUI() {
        if (map == null) {
            return
        }
        try {
            if (locationPermissionGranted) {
                map?.isMyLocationEnabled = true
                map?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                map?.isMyLocationEnabled = false
                map?.uiSettings?.isMyLocationButtonEnabled = false
                lastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    companion object {
        private val TAG = MapFragment::class.java.simpleName
        private const val DEFAULT_ZOOM = 15
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        private const val API = "AIzaSyATfNmd6D0HIN6nX_37e760xZUlq_LMZYQ"
        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"
    }

    override fun onDestroy() {
        super.onDestroy()
        map?.let {
            it.clear()
            it.mapType = GoogleMap.MAP_TYPE_NONE
        }
        map = null
        //fusedLocationProviderClient.removeLocationUpdates(callback)
        fusedLocationProviderClient.flushLocations()
        //fusedLocationProviderClient.removeLocationUpdates(callback)
        fusedLocationProviderClient.flushLocations()
    }
}
/*
    override fun onMapReady(map: GoogleMap) {

        this.map = map

        Log.d("MyTag", "JESTEM PRZED USTAIWANIEM ONFO NA MARKERZE")

        this.map?.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            // Return null here, so that getInfoContents() is called next.
            override fun getInfoWindow(arg0: Marker): View? {
                Log.d("MyTag", "W ŚRODKU GET INFO WINDOW")
                return null

            }

            override fun getInfoContents(marker: Marker): View {
                // Inflate the layouts for the info window, title and snippet.
                val inflater = LayoutInflater.from(context)
                val view = inflater.inflate(R.layout.custom_info_window, null)

                val infoWindow = layoutInflater.inflate(R.layout.custom_info_window,
                    view.findViewById<FrameLayout>(R.id.map_fragment), false)

                val title = infoWindow.findViewById<TextView>(R.id.titly)
                title.text = marker.title
                val snippet = infoWindow.findViewById<TextView>(R.id.snippet)
                snippet.text = marker.snippet
                Log.d("MyTag", "POD KONIEC GET INFO CONTENTS")
                return infoWindow
            }
        })



        val infoWindowAdapter = object : GoogleMap.InfoWindowAdapter {

            override fun getInfoContents(p0: Marker): View? {
                val inflater = LayoutInflater.from(context)
                val view = inflater.inflate(R.layout.custom_info_window, null)
                val textView = view.findViewById<TextView>(R.id.textView)
                textView.text = marker?.title
                return view
            }

            override fun getInfoWindow(p0: Marker): View? {
                return null
            }
        }
        }
        */
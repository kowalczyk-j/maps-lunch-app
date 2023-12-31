package pw.pap22z.bulionapp.ui.map


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.data.entities.Restaurant
import pw.pap22z.bulionapp.databinding.FragmentMapBinding
import pw.pap22z.bulionapp.ui.restaurant.RestaurantActivity
import java.util.*


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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION)
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION)

        }

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment

        mapViewModel = ViewModelProvider(this)[MapViewModel::class.java]
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

    private fun createDrawableFromView(context: Context, view: View): Bitmap {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        view.layoutParams =
            ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun updateMap() {
        map?.clear()
        for (restaurant in restaurants) {
            val markerView = layoutInflater.inflate(R.layout.item_map_marker, null)
            val markerImageView = markerView.findViewById<ImageView>(R.id.marker_image_view)
            val markerTextView = markerView.findViewById<TextView>(R.id.marker_text_view)
            markerImageView.setImageResource(R.drawable.marker_map_restaurant)
            markerTextView.text = restaurant.name

            val marker = map?.addMarker(
                MarkerOptions().position(LatLng(restaurant.latitude, restaurant.longitude)).title(restaurant.name)
                    .icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(requireActivity(), markerView)))
            )
            marker?.tag = restaurant
        }

        map?.setOnMarkerClickListener { marker ->
            val restaurant = marker.tag as Restaurant
            map?.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.position, DEFAULT_ZOOM.toFloat()))
            showRestaurantDialog(restaurant)
            true
        }
    }


    private fun showRestaurantDialog(restaurant: Restaurant) {
        val builder = AlertDialog.Builder(requireContext())
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_restaurant_map, null)

        val restaurantName = dialogView.findViewById<TextView>(R.id.name)
        restaurantName.text = restaurant.name
        val photo = dialogView.findViewById<ImageView>(R.id.logo)
        photo.setImageBitmap(restaurant.image_title)
        val restaurantPriceRating = dialogView.findViewById<TextView>(R.id.restaurant_price_rating)
        val formattedRating = if (restaurant.rating == 0f) "---" else "%.2f".format(restaurant.rating)
        restaurantPriceRating.text = getString(R.string.restaurant_price_rating, restaurant.price, formattedRating)
        val restaurantLunchHours = dialogView.findViewById<TextView>(R.id.restaurant_availability)
        restaurantLunchHours.text =
            getString(R.string.restaurant_availability, restaurant.hour_start, restaurant.hour_end)


        val route = dialogView.findViewById<Button>(R.id.button_route)
        route?.setOnClickListener {
            if (context != null) {
                val mapUri: Uri = Uri.parse("geo:0,0?q=" + Uri.encode(restaurant.address))
                val mapIntent = Intent(Intent.ACTION_VIEW, mapUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }
        }
        val infoButton = dialogView.findViewById<Button>(R.id.button_more)
        infoButton?.setOnClickListener {
            if (context != null) {
                val intent = Intent(context, RestaurantActivity::class.java)
                intent.putExtra("restaurant", restaurant)
                startActivity(intent)
            }
        }
        builder.setView(dialogView)
        builder.show()
    }


    override fun onMapReady(map: GoogleMap) {
        this.map = map

        getLocationPermission()
        updateLocationUI()
        getDeviceLocation()

        mapViewModel.allRestaurants.observe(viewLifecycleOwner) {
            restaurants = it
            updateMap()
        }

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
                            map?.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        lastKnownLocation!!.latitude, lastKnownLocation!!.longitude
                                    ), DEFAULT_ZOOM.toFloat()
                                )
                            )
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.")
                        Log.e(TAG, "Exception: %s", task.exception)
                        map?.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat())
                        )
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
        if (ContextCompat.checkSelfPermission(
                nowThis.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                nowThis, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    @Deprecated("Use the ActivityCompat#requestPermissions() method instead.")
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
        fusedLocationProviderClient.flushLocations()
    }
}

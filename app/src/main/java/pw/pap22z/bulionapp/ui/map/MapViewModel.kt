package pw.pap22z.bulionapp.ui.map

import android.app.Application
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.*
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import pw.pap22z.bulionapp.data.RestaurantDatabase
import pw.pap22z.bulionapp.data.entities.Restaurant

class MapViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var allRestaurants: LiveData<List<Restaurant>>
    val restaurantDao by lazy { RestaurantDatabase.getDatabase(application).restaurantDao() }

    init {
        allRestaurants = getRestaurants()
    }

    private fun getRestaurants(): LiveData<List<Restaurant>> {
        return restaurantDao.getRestaurants()
    }


/*
    fun updateRestaurantCoordinates() {
        viewModelScope.launch {
            val restaurants = allRestaurants.value
            restaurants?.forEach { restaurant ->
                if (restaurant.latitude == 0.0 && restaurant.longitude == 0.0) {
                    val latLng = getCoordinates(restaurant.address)
                    latLng?.let {
                        restaurant.latitude = it.latitude
                        restaurant.longitude = it.longitude
                        restaurantDao.updateCoordinates(restaurant.restaurant_id, it.latitude, it.longitude)
                    }
                }
            }
        }
    }

    private fun getCoordinates(address: String): LatLng? {
        val geocoder = Geocoder(getApplication())
        val likeLocation = address + "Warsaw, Poland"
        val addressList = geocoder.getFromLocationName(likeLocation, 1)
        if (addressList != null && addressList.isNotEmpty()) {
            val lat = addressList[0].latitude
            val lng = addressList[0].longitude
            Log.d("GEOCODER", "ZNALAZŁAEM współrzędne YEAH YEAY H EYAYAEY $lat, $lng)")
            return LatLng(lat, lng)
        } else {
            // Handle the case where no results are found
            Log.d("GEOCODER", "No results found for address: $address")
            return null
        }
    }
*/
}
package pw.pap22z.bulionapp.ui.search

import android.app.Application
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pw.pap22z.bulionapp.data.RestaurantDatabase
import pw.pap22z.bulionapp.data.entities.Restaurant


class SearchViewModel (application: Application) : AndroidViewModel(application){
    lateinit var allRestaurants : LiveData<List<Restaurant>>
    val dao by lazy { RestaurantDatabase.getDatabase(application).restaurantDao() }

    init {
        allRestaurants = getRestaurants()
    }

    fun getRestaurants(): LiveData<List<Restaurant>> {
        allRestaurants = dao.getRestaurants()
        return allRestaurants
    }

    fun insertRestaurant(restaurant: Restaurant) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertRestaurant(restaurant)
            val latlng = getCoordinates(restaurant.address)
            val latitude = latlng?.latitude
            val longitude = latlng?.longitude
            if(latitude != null && longitude != null){
                dao.updateCoordinates(restaurant.restaurant_id, latitude, longitude)
            }
        }
    }

    fun searchRestaurant(searchQuery: String): LiveData<List<Restaurant>> {
        allRestaurants = dao.searchRestaurant(searchQuery)
        return allRestaurants
    }

    fun sortRestaurantsByPrice(): LiveData<List<Restaurant>> {
        allRestaurants = dao.sortRestaurantsByPrice()
        return allRestaurants
    }

    fun sortRestaurantsByVege(): LiveData<List<Restaurant>> {
        allRestaurants = dao.sortRestaurantsByVege()
        return allRestaurants
    }


    fun sortRestaurantsByDishes(): LiveData<List<Restaurant>> {
        allRestaurants = dao.sortRestaurantsByDishes()
        return allRestaurants
    }


    fun sortRestaurantsByPriceDESC(): LiveData<List<Restaurant>> {
        allRestaurants = dao.sortRestaurantsByPriceDESC()
        return allRestaurants
    }


    fun sortRestaurantsByRating(): LiveData<List<Restaurant>> {
        allRestaurants = dao.sortRestaurantsByRating()
        return allRestaurants
    }


    fun sortRestaurantsByCloseHour(): LiveData<List<Restaurant>> {
        allRestaurants = dao.sortRestaurantsByCloseHour()
        return allRestaurants
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
}
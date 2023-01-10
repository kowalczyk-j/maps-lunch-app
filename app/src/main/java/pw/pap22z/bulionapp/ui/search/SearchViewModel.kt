package pw.pap22z.bulionapp.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
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
}
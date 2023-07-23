package pw.pap22z.bulionapp.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import pw.pap22z.bulionapp.data.RestaurantDatabase
import pw.pap22z.bulionapp.data.entities.Restaurant


class SearchViewModel (application: Application) : AndroidViewModel(application){
    var allRestaurants : LiveData<List<Restaurant>>
    private val restaurantDao by lazy { RestaurantDatabase.getDatabase(application).restaurantDao() }

    init {
        allRestaurants = getRestaurants()
    }

    fun getRestaurants(): LiveData<List<Restaurant>> {
        allRestaurants = restaurantDao.getRestaurants()
        return allRestaurants
    }

    fun searchRestaurant(searchQuery: String): LiveData<List<Restaurant>> {
        allRestaurants = restaurantDao.searchRestaurant(searchQuery)
        return allRestaurants
    }

    fun sortRestaurantsByPrice(): LiveData<List<Restaurant>> {
        allRestaurants = restaurantDao.sortRestaurantsByPrice()
        return allRestaurants
    }

    fun sortRestaurantsByVegan(): LiveData<List<Restaurant>> {
        allRestaurants = restaurantDao.sortRestaurantsByVegan()
        return allRestaurants
    }


    fun sortRestaurantsByDishes(): LiveData<List<Restaurant>> {
        allRestaurants = restaurantDao.sortRestaurantsByDishes()
        return allRestaurants
    }


    fun sortRestaurantsByPriceDESC(): LiveData<List<Restaurant>> {
        allRestaurants = restaurantDao.sortRestaurantsByPriceDESC()
        return allRestaurants
    }


    fun sortRestaurantsByRating(): LiveData<List<Restaurant>> {
        allRestaurants = restaurantDao.sortRestaurantsByRating()
        return allRestaurants
    }


    fun sortRestaurantsByCloseHour(): LiveData<List<Restaurant>> {
        allRestaurants = restaurantDao.sortRestaurantsByCloseHour()
        return allRestaurants
    }
}
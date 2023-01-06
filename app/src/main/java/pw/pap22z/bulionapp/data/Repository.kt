package pw.pap22z.bulionapp.data

import androidx.lifecycle.LiveData
import pw.pap22z.bulionapp.data.entities.Restaurant


class Repository(private val restaurantDao: RestaurantDao) {

    val allRestaurants: LiveData<List<Restaurant>> = restaurantDao.readData()

//    fun readData(): LiveData<List<Restaurant>> {
//        return restaurantDao.readData()
//        //Flow można zmienić na LiveData
//    }

    suspend fun insertData(restaurant: Restaurant) {
        restaurantDao.insertData(restaurant)
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Restaurant>> {
        return restaurantDao.searchDatabase(searchQuery)
    }

}
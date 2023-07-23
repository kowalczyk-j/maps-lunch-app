package pw.pap22z.bulionapp.ui.map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import pw.pap22z.bulionapp.data.RestaurantDatabase
import pw.pap22z.bulionapp.data.entities.Restaurant

class MapViewModel(application: Application) : AndroidViewModel(application) {
    var allRestaurants: LiveData<List<Restaurant>>
    private val restaurantDao by lazy { RestaurantDatabase.getDatabase(application).restaurantDao() }

    init {
        allRestaurants = getRestaurants()
    }

    private fun getRestaurants(): LiveData<List<Restaurant>> {
        return restaurantDao.getRestaurants()
    }
}
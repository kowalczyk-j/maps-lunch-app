package pw.pap22z.bulionapp.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import pw.pap22z.bulionapp.data.RestaurantDatabase
import pw.pap22z.bulionapp.data.entities.Restaurant

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    var favoriteRestaurants : LiveData<List<Restaurant>>

    private val restaurantDao by lazy {RestaurantDatabase.getDatabase(application).restaurantDao()}

    init {
        favoriteRestaurants = getRestaurants()
    }

    private fun getRestaurants(): LiveData<List<Restaurant>> {
        return restaurantDao.getFavoriteRestaurants()
    }

}
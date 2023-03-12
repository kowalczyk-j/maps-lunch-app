package pw.pap22z.bulionapp.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pw.pap22z.bulionapp.data.RestaurantDatabase
import pw.pap22z.bulionapp.data.entities.Lunch
import pw.pap22z.bulionapp.data.entities.Restaurant

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    var favoriteRestaurants : LiveData<List<Restaurant>>
    private var allLunches : LiveData<List<Lunch>>
    var lunch = MutableLiveData<Lunch>()

    private val restaurantDao by lazy {RestaurantDatabase.getDatabase(application).restaurantDao()}
    private val lunchDao by lazy {RestaurantDatabase.getDatabase(application).lunchDao()}

    init {
        favoriteRestaurants = getRestaurants()
        allLunches = getLunches()
    }

    private fun getRestaurants(): LiveData<List<Restaurant>> {
        return restaurantDao.getFavoriteRestaurants()
    }

    private fun getLunches(): LiveData<List<Lunch>> {
        return lunchDao.getLunches()
    }

}
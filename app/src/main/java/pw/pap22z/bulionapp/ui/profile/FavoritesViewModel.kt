package pw.pap22z.bulionapp.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pw.pap22z.bulionapp.data.RestaurantDatabase
import pw.pap22z.bulionapp.data.entities.Lunch
import pw.pap22z.bulionapp.data.entities.Restaurant

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var favoriteRestaurants : LiveData<List<Restaurant>>
    lateinit var allLunches : LiveData<List<Lunch>>
    var lunch = MutableLiveData<Lunch>()

    val restaurantDao by lazy {RestaurantDatabase.getDatabase(application).restaurantDao()}
    val lunchDao by lazy {RestaurantDatabase.getDatabase(application).lunchDao()}

    init {
        favoriteRestaurants = getRestaurants()
        allLunches = getLunches()
    }

    fun getRestaurants(): LiveData<List<Restaurant>> {
        return restaurantDao.getFavoriteRestaurants()
    }

    fun getLunches(): LiveData<List<Lunch>> {
        return lunchDao.getLunches()
    }

}
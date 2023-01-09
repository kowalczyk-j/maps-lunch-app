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
    lateinit var allRestaurants : LiveData<List<Restaurant>>
    lateinit var allLunches : LiveData<List<Lunch>>
    var lunch = MutableLiveData<Lunch>()

    val restaurantDao by lazy {RestaurantDatabase.getDatabase(application).restaurantDao()}
    val lunchDao by lazy {RestaurantDatabase.getDatabase(application).lunchDao()}

    init {
        allRestaurants = getRestaurants()
        allLunches = getLunches()
    }

    fun getRestaurants(): LiveData<List<Restaurant>> {
        return restaurantDao.getRestaurants()
    }

    fun getLunches(): LiveData<List<Lunch>> {
        return lunchDao.getLunches()
    }

    fun getLunchWithRestaurant(restaurantId: Int) {
        viewModelScope.launch {
            lunch.postValue(lunchDao.getLunchWithRestaurant(restaurantId))
        }
    }

    fun insertRestaurant(restaurant: Restaurant) {
        viewModelScope.launch(Dispatchers.IO) {
            restaurantDao.insertRestaurant(restaurant)
        }
    }

    fun insertLunch(lunch: Lunch) {
        viewModelScope.launch(Dispatchers.IO) {
            lunchDao.insertLunch(lunch)
        }
    }

}
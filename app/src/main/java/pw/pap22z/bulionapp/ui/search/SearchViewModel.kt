package pw.pap22z.bulionapp.ui.search

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


class SearchViewModel (application: Application) : AndroidViewModel(application){
    lateinit var allRestaurants : LiveData<List<Restaurant>>
    lateinit var allLunches : LiveData<List<Lunch>>
    var lunch = MutableLiveData<Lunch>()

    val dao by lazy { RestaurantDatabase.getDatabase(application).restaurantDao() }
    val ldao by lazy { RestaurantDatabase.getDatabase(application).lunchDao() }

    init {
        allRestaurants = getRestaurants()
        allLunches = getLunches()
    }

    fun getLunchWithRestaurant(restaurant_id: Int) {
        viewModelScope.launch {
            lunch.postValue(ldao.getLunchWithRestaurant(restaurant_id))
        }
    }

    fun getRestaurants(): LiveData<List<Restaurant>> {
        return dao.getRestaurants()
    }

    fun sortRestaurantsByName(): LiveData<List<Restaurant>> {
        return dao.sortRestaurantsByName()
    }


    fun getLunches() : LiveData<List<Lunch>> {
        return ldao.getLunches()
    }

    fun insertRestaurant(restaurant: Restaurant) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertRestaurant(restaurant)
        }
    }

    fun insertLunch(lunch: Lunch) {
        viewModelScope.launch(Dispatchers.IO) {
            ldao.insertLunch(lunch)
        }
    }

    fun searchRestaurant(searchQuery: String): LiveData<List<Restaurant>> {
        return dao.searchRestaurant(searchQuery)
    }
}
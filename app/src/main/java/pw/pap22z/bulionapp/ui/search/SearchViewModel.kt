package pw.pap22z.bulionapp.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pw.pap22z.bulionapp.data.Repository
import pw.pap22z.bulionapp.data.RestaurantDatabase
import pw.pap22z.bulionapp.data.entities.Restaurant


class SearchViewModel (application: Application) : AndroidViewModel(application){
    val allRestaurants : LiveData<List<Restaurant>>
    val repository: Repository

    init {
        val dao = RestaurantDatabase.getDatabase(application).restaurantDao()
        repository = Repository(dao)
        allRestaurants = repository.allRestaurants
    }

    fun insertData(restaurant: Restaurant) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(restaurant)
        }
    fun searchDatabase(searchQuery: String): LiveData<List<Restaurant>> {
        return repository.searchDatabase(searchQuery)
    }
}
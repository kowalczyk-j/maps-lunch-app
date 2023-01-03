package pw.pap22z.bulionapp.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import pw.pap22z.bulionapp.data.Repository
import pw.pap22z.bulionapp.data.Restaurant
import pw.pap22z.bulionapp.data.RestaurantDatabase


class SearchViewModel (application: Application) : AndroidViewModel(application){
    val allRestaurants : Flow<List<Restaurant>>
    val repository: Repository

    init {
        val dao = RestaurantDatabase.getDatabase(application).restaurantDao()
        repository = Repository(dao)
        allRestaurants = repository.readData()
    }

    fun insertData(restaurant: Restaurant) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(restaurant)
        }
//    fun searchDatabase(searchQuery: String): LiveData<List<Restaurant>> {
//        return repository.searchDatabase(searchQuery).asLiveData()
//    }
}
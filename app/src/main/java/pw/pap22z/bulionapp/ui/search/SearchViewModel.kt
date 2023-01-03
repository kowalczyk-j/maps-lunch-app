package pw.pap22z.bulionapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pw.pap22z.bulionapp.data.Restaurant
import pw.pap22z.bulionapp.data.RestaurantDao
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val  repository: RestaurantDao,
) : ViewModel()
{
    val readData = repository.readData().asLiveData()

    fun insertData(restaurant: Restaurant){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(restaurant)
        }
    }
    fun searchDatabase(searchQuery: String): LiveData<List<Restaurant>> {
        return repository.searchDatabase(searchQuery).asLiveData()
    }
}
package pw.pap22z.bulionapp.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pw.pap22z.bulionapp.data.RestaurantDatabase
import pw.pap22z.bulionapp.data.entities.Restaurant
import pw.pap22z.bulionapp.data.entities.Review

class MyReviewsViewModel(application: Application) : AndroidViewModel(application){
    lateinit var allReviews: LiveData<List<Review>>
    lateinit var allRestaurants: LiveData<List<Restaurant>>
    var restaurant = MutableLiveData<Restaurant>()

    val reviewDao by lazy {RestaurantDatabase.getDatabase(application).reviewDao()}
    val restaurantDao by lazy {RestaurantDatabase.getDatabase(application).restaurantDao()}

    init {
        allReviews = getReviews()
        allRestaurants = getRestaurants()
    }

    fun getReviews(): LiveData<List<Review>> {
        return reviewDao.getReviews()
    }

    fun getRestaurants(): LiveData<List<Restaurant>> {
        return restaurantDao.getRestaurants()
    }
}
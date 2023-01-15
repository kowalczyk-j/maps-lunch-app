package pw.pap22z.bulionapp.ui.restaurant

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pw.pap22z.bulionapp.data.RestaurantDatabase
import pw.pap22z.bulionapp.data.entities.Review

class RestaurantViewModel(application: Application): AndroidViewModel(application) {
    lateinit var reviews: LiveData<List<Review>>

    val reviewDao by lazy {RestaurantDatabase.getDatabase(application).reviewDao()}
    val restaurantDao by lazy {RestaurantDatabase.getDatabase(application).restaurantDao()}

    fun getRestaurantReviews(restaurantId: Int): LiveData<List<Review>> {
        reviews = reviewDao.getReviewsWithRestaurant(restaurantId)
        return reviews
    }

    fun insertReview(review: Review) {
        viewModelScope.launch(Dispatchers.IO) {
            reviewDao.insertReview(review)
        }
    }

   suspend fun getRestaurantRating(restaurantId: Int): Float {
        return reviewDao.getRestaurantRating(restaurantId)
    }

    suspend fun updateRating(restaurantId: Int, rating: Float) {
        return restaurantDao.updateRating(restaurantId, rating)
    }
}
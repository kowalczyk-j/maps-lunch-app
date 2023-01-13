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

    fun getRestaurantReviews(restaurantId: Int): LiveData<List<Review>> {
        reviews = reviewDao.getReviewsWithRestaurant(restaurantId)
        return reviews
    }

    fun insertReview(review: Review) {
        viewModelScope.launch(Dispatchers.IO) {
            reviewDao.insertReview(review)
        }
    }

    fun updateReviewUsername(userId: Int, newUsername: String) {
        viewModelScope.launch(Dispatchers.IO) {
            reviewDao.updateReviewUsername(userId, newUsername)
        }
    }

    fun updateReviewProfilePic(userId: Int, newProfilePic: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            reviewDao.updateReviewProfilePic(userId, newProfilePic)
        }
    }

}
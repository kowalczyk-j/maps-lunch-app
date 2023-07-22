package pw.pap22z.bulionapp.ui.restaurant

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pw.pap22z.bulionapp.data.RestaurantDatabase
import pw.pap22z.bulionapp.data.entities.Restaurant
import pw.pap22z.bulionapp.data.entities.Review
import pw.pap22z.bulionapp.data.entities.User

class RestaurantViewModel(application: Application): AndroidViewModel(application) {
    lateinit var reviews: LiveData<List<Review>>

    private val reviewDao by lazy {RestaurantDatabase.getDatabase(application).reviewDao()}
    private val restaurantDao by lazy {RestaurantDatabase.getDatabase(application).restaurantDao()}
    private val userDao by lazy {RestaurantDatabase.getDatabase(application).userDao()}

    fun getRestaurantReviews(restaurantId: Int): LiveData<List<Review>> {
        reviews = reviewDao.getReviewsWithRestaurant(restaurantId)
        return reviews
    }

    fun insertReview(review: Review) {
        viewModelScope.launch(Dispatchers.IO) {
            reviewDao.insertReview(review)
        }
    }

    fun deleteReview(review: Review) {
        viewModelScope.launch(Dispatchers.IO) {
            reviewDao.deleteReview(review)
        }
    }

    fun addToFavorites(restaurantId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            restaurantDao.addToFavorites(restaurantId)
        }
    }

    fun removeFromFavorites(restaurantId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            restaurantDao.removeFromFavorites(restaurantId)
        }
    }

    fun getRestaurantById(restaurantId: Int): Restaurant {
        return restaurantDao.getRestaurantById(restaurantId)
    }

   suspend fun getRestaurantRating(restaurantId: Int): Float {
        return reviewDao.getRestaurantRating(restaurantId)
    }

    suspend fun updateRating(restaurantId: Int, rating: Float) {
        return restaurantDao.updateRating(restaurantId, rating)
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
    suspend fun getUser(userId: Int) : User {
        return userDao.getUser(userId)
    }
    suspend fun getReviewByUserAndRestaurant(userId: Int, restaurantId: Int): Review? {
        return reviewDao.getReviewByUserAndRestaurant(userId, restaurantId)
    }
}
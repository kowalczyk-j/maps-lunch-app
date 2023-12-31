package pw.pap22z.bulionapp.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import pw.pap22z.bulionapp.data.RestaurantDatabase
import pw.pap22z.bulionapp.data.entities.Review

class MyReviewsViewModel(application: Application) : AndroidViewModel(application) {
    var userReviews: LiveData<List<Review>>

    private val reviewDao by lazy { RestaurantDatabase.getDatabase(application).reviewDao() }

    init {
        userReviews = getReviewsWithUser(1)
    }

    private fun getReviewsWithUser(userId: Int): LiveData<List<Review>> {
        userReviews = reviewDao.getReviewsWithUser(userId)
        return userReviews
    }
}
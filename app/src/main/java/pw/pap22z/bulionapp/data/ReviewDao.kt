package pw.pap22z.bulionapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pw.pap22z.bulionapp.data.entities.Review

@Dao
interface ReviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReview(review: Review)

//    @Query("SELECT * FROM review ORDER BY review_id ASC")
//    fun getReviews(): LiveData<ArrayList<Review>>

    @Query("SELECT * FROM review WHERE restaurant_id = :restaurantId")
    fun getReviewsWithRestaurant(restaurantId: Int): LiveData<List<Review>>

    @Query("SELECT * FROM review WHERE user_id = :userId")
    fun getReviewsWithUser(userId: Int): LiveData<List<Review>>

    @Query("SELECT AVG(review_rating) FROM review WHERE restaurant_id = :restaurantId")
    suspend fun getRestaurantRating(restaurantId: Int) : Float

}
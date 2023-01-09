package pw.pap22z.bulionapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import pw.pap22z.bulionapp.data.entities.Review

@Dao
interface ReviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReview(review: Review)

//    @Query("SELECT * FROM review ORDER BY review_id ASC")
//    fun getReviews(): LiveData<ArrayList<Review>>

//    @Query("SELECT * FROM review WHERE restaurantId = :restaurantId")
//    suspend fun getReviewsWithRestaurant(restaurantId: Int): ArrayList<Review>
}
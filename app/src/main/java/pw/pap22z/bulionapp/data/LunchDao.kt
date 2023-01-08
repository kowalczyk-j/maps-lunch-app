package pw.pap22z.bulionapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import pw.pap22z.bulionapp.data.entities.Lunch

@Dao
interface LunchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLunch(lunch: Lunch)

    @Query("SELECT * FROM lunch ORDER BY lunch_id ASC")
    fun getLunches(): LiveData<List<Lunch>>

    @Query("SELECT * FROM lunch WHERE restaurantId = :restaurantId")
    suspend fun getLunchWithRestaurant(restaurantId: Int): Lunch
}
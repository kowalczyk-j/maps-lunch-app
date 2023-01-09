package pw.pap22z.bulionapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import pw.pap22z.bulionapp.data.entities.Restaurant
import pw.pap22z.bulionapp.data.entities.Review

//Dao - database access object, tutaj zapisuje się SQLowe rzeczy
@Dao
interface RestaurantDao {

    @Query("SELECT * FROM restaurant ORDER BY restaurant_id ASC")
    fun getRestaurants(): LiveData<List<Restaurant>>
    //LiveData - przepływ służący do optymalnego zarządzania wątkami przy obsługiwaniu
    //wielu danych, jeżeli jest to nie trzeba robić suspend fun

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurant(restaurant: Restaurant)
    //suspend - Można zawiesić wykonanie funkcji do późniejszego wykonania bez blokowania głownego wątku.

    @Query("SELECT * FROM restaurant WHERE titleImage LIKE :searchQuery ORDER BY restaurant_id ASC")
    fun searchRestaurant(searchQuery: String): LiveData<List<Restaurant>>

    @Update
    suspend fun update(restaurant: Restaurant)

    @Delete
    suspend fun delete(restaurant: Restaurant)

}
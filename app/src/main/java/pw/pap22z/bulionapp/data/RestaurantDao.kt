package pw.pap22z.bulionapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import pw.pap22z.bulionapp.data.entities.Restaurant

//Dao - database access object, tutaj zapisuje się SQLowe rzeczy
@Dao
interface RestaurantDao {

    @Query("SELECT * FROM restaurant ORDER BY lower(name) ASC")
    fun getRestaurants(): LiveData<List<Restaurant>>

    @Query("SELECT * FROM restaurant WHERE restaurant_id = :id")
    fun getRestaurantById(id: Int): LiveData<Restaurant>
    //LiveData - przepływ służący do optymalnego zarządzania wątkami przy obsługiwaniu
    //wielu danych, jeżeli jest to nie trzeba robić suspend fun

    @Query("SELECT * FROM restaurant WHERE is_vege = 1 ORDER BY restaurant_id ASC")
    fun sortRestaurantsByVege(): LiveData<List<Restaurant>>

    @Query("SELECT * FROM restaurant ORDER BY num_dishes DESC")
    fun sortRestaurantsByDishes(): LiveData<List<Restaurant>>

    @Query("SELECT * FROM restaurant ORDER BY price ASC")
    fun sortRestaurantsByPrice(): LiveData<List<Restaurant>>

    @Query("SELECT * FROM restaurant ORDER BY price DESC")
    fun sortRestaurantsByPriceDESC(): LiveData<List<Restaurant>>

    @Query("SELECT * FROM restaurant ORDER BY rating DESC")
    fun sortRestaurantsByRating(): LiveData<List<Restaurant>>

    @Query("SELECT * FROM restaurant ORDER BY hour_end DESC")
    fun sortRestaurantsByCloseHour(): LiveData<List<Restaurant>>

    @Query("UPDATE restaurant SET latitude = :latitude, longitude = :longitude WHERE restaurant_id = :restaurantId")
    suspend fun updateCoordinates(restaurantId: Int, latitude: Double, longitude: Double)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurant(restaurant: Restaurant)
    //suspend - Można zawiesić wykonanie funkcji do późniejszego wykonania bez blokowania głownego wątku.

    @Query("SELECT * FROM restaurant WHERE name LIKE :searchQuery or lunch_info LIKE :searchQuery or cuisine_type LIKE :searchQuery ORDER BY lower(name) ASC")
    fun searchRestaurant(searchQuery: String): LiveData<List<Restaurant>>

    @Query("UPDATE restaurant SET lunch_info = :newLunch WHERE restaurant_id = :restaurantId")
    suspend fun updateLunch(restaurantId: Int, newLunch: String)

    @Delete
    suspend fun delete(restaurant: Restaurant)

}
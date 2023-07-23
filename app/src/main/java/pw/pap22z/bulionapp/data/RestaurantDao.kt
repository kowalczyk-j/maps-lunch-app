package pw.pap22z.bulionapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import pw.pap22z.bulionapp.data.entities.Restaurant

@Dao
interface RestaurantDao {

    @Query("SELECT * FROM restaurant ORDER BY lower(name) ASC")
    fun getRestaurants(): LiveData<List<Restaurant>>

    @Query("SELECT * FROM restaurant WHERE restaurant_id = :id")
    fun getRestaurantById(id: Int): Restaurant

    @Query("SELECT * FROM restaurant WHERE favorite = 1")
    fun getFavoriteRestaurants(): LiveData<List<Restaurant>>

    @Query("SELECT * FROM restaurant WHERE is_vegan = 1 ORDER BY restaurant_id ASC")
    fun sortRestaurantsByVegan(): LiveData<List<Restaurant>>

    @Query("SELECT * FROM restaurant ORDER BY dishes_count DESC")
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

    @Query("UPDATE restaurant SET rating = :rating WHERE restaurant_id = :restaurantId")
    suspend fun updateRating(restaurantId: Int, rating: Float)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRestaurant(restaurant: Restaurant)

    @Query("SELECT * FROM restaurant WHERE name LIKE :searchQuery or lunch_info LIKE :searchQuery or cuisine_type LIKE :searchQuery ORDER BY lower(name) ASC")
    fun searchRestaurant(searchQuery: String): LiveData<List<Restaurant>>

    @Query("UPDATE restaurant SET lunch_info = :newLunch WHERE restaurant_id = :restaurantId")
    suspend fun updateLunch(restaurantId: Int, newLunch: String)

    @Query("UPDATE restaurant SET menu = :newMenu WHERE restaurant_id = :restaurantId")
    suspend fun updateMenu(restaurantId: Int, newMenu: String)

    @Query("UPDATE restaurant SET edit_date = :newDate WHERE restaurant_id = :restaurantId")
    suspend fun updateEditDate(restaurantId: Int, newDate: String)

    @Query("UPDATE restaurant SET favorite = 1 WHERE restaurant_id = :restaurantId")
    suspend fun addToFavorites(restaurantId: Int)

    @Query("UPDATE restaurant SET favorite = 0 WHERE restaurant_id = :restaurantId")
    suspend fun removeFromFavorites(restaurantId: Int)

    @Delete
    suspend fun delete(restaurant: Restaurant)

}
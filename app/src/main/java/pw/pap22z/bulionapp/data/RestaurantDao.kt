package pw.pap22z.bulionapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import pw.pap22z.bulionapp.data.entities.Restaurant

//Dao - database access object, tutaj zapisuje się SQLowe rzeczy
@Dao
interface RestaurantDao {

    @Query("SELECT * FROM restaurant_table ORDER BY id ASC")
    fun readData(): LiveData<List<Restaurant>>
    //LiveData - przepływ służący do optymalnego zarządzania wątkami przy obsługiwaniu
    //wielu danych, jeżeli jest to nie trzeba robić suspend fun

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //jeżeli bedzie insert rekordu o takim samym id to go zastapi (malo istotne)
    suspend fun insertData(restaurant: Restaurant)
    //suspend - Można zawiesić wykonanie funkcji do późniejszego wykonania bez blokowania głownego wątku.

    @Query("SELECT * FROM restaurant_table WHERE title LIKE :searchQuery ORDER BY id ASC") //OR title LIKE 'si%'
    fun searchDatabase(searchQuery: String): LiveData<List<Restaurant>>

    @Update
    suspend fun update(restaurant: Restaurant)

    @Delete
    suspend fun delete(restaurant: Restaurant)

}
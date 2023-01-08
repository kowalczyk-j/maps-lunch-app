package pw.pap22z.bulionapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pw.pap22z.bulionapp.data.entities.Lunch
import pw.pap22z.bulionapp.data.entities.Restaurant

//tutaj jest definiowana baza danych z encjami
@Database(
    entities = [Restaurant::class, Lunch::class], //lista encji
    version = 1,    //nieistotne
    exportSchema = false
)
abstract class RestaurantDatabase: RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao
    abstract fun lunchDao(): LunchDao

    companion object {
        // Singleton gwarantuje jedną instację bazy w czasie uzytkowania
        @Volatile
        private var INSTANCE: RestaurantDatabase? = null

        fun getDatabase(context: Context): RestaurantDatabase {
            // Jeśli INSTANCE nie jest nullem, zwróc bazę
            // w przeciwnym razie utwórz ją
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RestaurantDatabase::class.java,
                    "restaurant_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}

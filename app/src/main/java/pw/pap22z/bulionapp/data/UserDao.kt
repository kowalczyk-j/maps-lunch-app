package pw.pap22z.bulionapp.data

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.*
import androidx.room.Query
import pw.pap22z.bulionapp.data.entities.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE user_id = :id")
    suspend fun getUser(id: Int): User

    @Query("UPDATE user SET username = :username WHERE user_id = :id")
    suspend fun updateUsername(id: Int, username: String)

    @Query("UPDATE user SET profile_pic = :pic WHERE user_id = :id")
    suspend fun updateProfilePic(id: Int, pic: Bitmap)

    @Insert(onConflict = REPLACE)
    suspend fun insertUser(user: User)

}
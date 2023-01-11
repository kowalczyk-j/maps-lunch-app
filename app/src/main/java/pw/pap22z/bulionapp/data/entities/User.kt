package pw.pap22z.bulionapp.data.entities

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class User(
    @PrimaryKey val user_id: Int,
    val username: String,
    val profile_pic: Bitmap?
): Parcelable

package pw.pap22z.bulionapp.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Lunch(
    @PrimaryKey val lunch_id: Int,
    val lunch_body: String,
    val restaurantId: Int
): Parcelable
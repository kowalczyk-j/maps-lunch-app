package pw.pap22z.bulionapp.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Review(
    @PrimaryKey val review_id: Int,
    val rating: Int,
    val review_body: String,
    val restaurantId: Int
): Parcelable

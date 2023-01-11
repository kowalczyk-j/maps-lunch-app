package pw.pap22z.bulionapp.data.entities

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Review(
    @PrimaryKey(autoGenerate = true) val review_id: Int = 0,
    val review_rating: Int,
    val review_body: String,
    @Embedded val restaurant: Restaurant?,
    @Embedded val user: User
): Parcelable

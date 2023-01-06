package pw.pap22z.bulionapp.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Review(
    val review_body: String,
    val restaurant_id: Int
): Parcelable{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

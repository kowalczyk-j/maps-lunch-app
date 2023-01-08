package pw.pap22z.bulionapp.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

//zdefiniowanie encji = tabeli
@Parcelize
@Entity
data class Restaurant(
    @PrimaryKey val restaurant_id: Int,
    val titleImage: String,
    val description: String
): Parcelable
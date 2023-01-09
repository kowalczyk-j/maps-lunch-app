package pw.pap22z.bulionapp.data.entities

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.io.Serializable

//zdefiniowanie encji = tabeli
@Parcelize
@Entity
data class Restaurant(
    @PrimaryKey val restaurant_id: Int,
    val titleImage: Bitmap,
    val description: String
): Parcelable, Serializable
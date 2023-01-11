package pw.pap22z.bulionapp.data.entities

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Restaurant(
    @PrimaryKey val restaurant_id: Int,
    val titleImage: Bitmap,
    val name: String,
    val rating: Float,
    val cuisine_type: String,
    val address: String,
    // Lunch info
    val price: Float,
    val hour_start: Int,
    val hour_end: Int,
    val num_dishes: Int,
    val is_vege: Boolean,
    val lunch_info: String,
    var latitude: Double = 0.0,
    var longitude: Double = 0.0
): Parcelable
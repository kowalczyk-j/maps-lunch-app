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
    val image_title: Bitmap,
    val name: String,
    val rating: Float = 0.0f,
    val cuisine_type: String,
    val address: String,
    // Lunch info
    val price: Float,
    val hour_start: Int,
    val hour_end: Int,
    val dishes_count: Int,
    val is_vegan: Boolean,
    val lunch_info: String,
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    val menu: String = "",
    val favorite: Boolean = false,
    val edit_date: String = ""
): Parcelable
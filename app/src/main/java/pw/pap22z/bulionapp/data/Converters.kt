package pw.pap22z.bulionapp.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class Converters {

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap?): ByteArray {
        if (bitmap != null) {
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            return outputStream.toByteArray()
        }
        return ByteArray(0)
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray): Bitmap? {
        return if (byteArray.isNotEmpty()) {
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        } else null
    }


}
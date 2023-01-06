package pw.pap22z.bulionapp.data.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

//zdefiniowanie encji = tabeli
@Parcelize
@Entity(tableName = "restaurant_table")
data class Restaurant(
    //można zmienić var na val
    @ColumnInfo(name = "title") val titleImage: String,
    @ColumnInfo(name = "description") val description: String
): Parcelable {
    @PrimaryKey(autoGenerate = true) //klucz główny generuje sie sam - nie podawać przy inicjalizacji
    var id: Int = 0
}

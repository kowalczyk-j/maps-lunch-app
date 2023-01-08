package pw.pap22z.bulionapp.src

import android.net.Uri
import java.io.Serializable

class User constructor(username: String) : Serializable {
    var username: String = username
    val favorites: MutableList<Restaurant> = mutableListOf()
    val reviews: ArrayList<Review> = arrayListOf()
    var profilePicture: Uri? = null
}
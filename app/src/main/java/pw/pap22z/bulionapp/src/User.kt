package pw.pap22z.bulionapp.src

import android.net.Uri

class User constructor(username: String){
    var username: String = username
    val favorites: MutableList<Restaurant> = mutableListOf()
    val reviews: ArrayList<Review> = arrayListOf()
    var profilePicture: Uri? = null

    fun addFavorite(restaurant: Restaurant) {
        favorites.add(restaurant)
    }

    fun addReview(review: Review) {
        reviews.add(review)
    }
}
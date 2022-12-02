package pw.pap22z.bulionapp.src

class User constructor(username: String){
    var username: String = username
    val favorites: MutableList<Restaurant> = mutableListOf()
    val reviews: ArrayList<Review> = arrayListOf()

    fun addFavorite(restaurant: Restaurant) {
        favorites.add(restaurant)
    }

    fun addReview(review: Review) {
        reviews.add(review)
    }
}
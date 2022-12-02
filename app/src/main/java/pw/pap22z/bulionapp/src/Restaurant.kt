package pw.pap22z.bulionapp.src

import android.location.Address

class Restaurant constructor(val name: String, val address: Address, val menu: List<Dish>) {
    val reviews: MutableList<Review> = mutableListOf()
    var rating: Double? = null

    private fun updateRating() {
        if (reviews.size != 0) {
            var sum: Double = 0.0
            for (review in reviews) {
                sum += review.rating
            }
            rating = sum/reviews.size
        }
    }

    fun addReview(review: Review) {
        reviews.add(review)
        updateRating()
    }
}
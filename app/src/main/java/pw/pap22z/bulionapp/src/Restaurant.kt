package pw.pap22z.bulionapp.src

import android.graphics.drawable.Drawable


class Restaurant constructor(val name: String, val address: String, val logo: Drawable, val menu: String, val hourStart: String, val hourEnd: String) {
    val reviews: ArrayList<Review> = arrayListOf()
    var rating: Double = 0.0

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
package pw.pap22z.bulionapp.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pw.pap22z.bulionapp.databinding.ActivityMyReviewsBinding
import pw.pap22z.bulionapp.src.Restaurant
import pw.pap22z.bulionapp.src.Review

class MyReviewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyReviewsBinding
    private var reviewList: ArrayList<Review> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyReviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurants = arrayOf(
            Restaurant("Fast Food", "Warszawa"),
            Restaurant("Chińczyk", "Warszawa"),
            Restaurant("Pizzeria", "Warszawa"),
            Restaurant("Caffe", "Warszawa"),
            Restaurant("Kebab", "Warszawa")
        )

        val ratings = arrayOf(
            5.0,
            3.5,
            4.5,
            3.5,
            3.0
        )

        val reviews = arrayOf(
            "Bardzo smaczne jedzenie, świetna atmosfera. Będę polecać wszystkim znajomym :)",
            "Niedopieczony kurczak",
            "Wszystko w porządku",
            "Jedzenie w porządku, natomiast czystość i obsługa pozostawia dużo do życzenia",
            "Otrzymałam nie to co zamawiałam"
        )

        for(i in restaurants.indices) {
            reviewList.add(Review(ratings[i], reviews[i], restaurants[i]))
        }

        binding.listReviews.adapter = ReviewsAdapter(this, reviewList)
    }
}
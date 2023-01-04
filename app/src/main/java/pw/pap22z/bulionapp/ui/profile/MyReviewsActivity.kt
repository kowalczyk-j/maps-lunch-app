package pw.pap22z.bulionapp.ui.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.databinding.ActivityMyReviewsBinding
import pw.pap22z.bulionapp.src.Restaurant
import pw.pap22z.bulionapp.src.Review
import pw.pap22z.bulionapp.src.User

class MyReviewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyReviewsBinding
    private var reviewList: ArrayList<Review> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyReviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurants = arrayOf(
            getDrawable(R.drawable.a)?.let {
                Restaurant("Aioli", "Świętokrzyska 18, Warszawa",
                    it, "Lunch 3-daniowy: 29 zł", "12.00", "17.00"
                )
            },
            getDrawable(R.drawable.b)?.let {
                Restaurant("Bordo Bistro", "Świętokrzyska 18, Warszawa",
                    it, "Lunch 3-daniowy: 23 zł", "12.00", "15.00"
                )
            },getDrawable(R.drawable.c)?.let {
                Restaurant("Marcello", "Świętokrzyska 18, Warszawa",
                    it, "Danie dnia + napój: 29 zł", "15.00", "17.00"
                )
            },
        )

        val ratings = arrayOf(
            5.0,
            3.5,
            4.5
        )

        val reviews = arrayOf(
            "Bardzo smaczne jedzenie, świetna atmosfera. Będę polecać wszystkim znajomym :)",
            "Wszystko w porządku",
            "Jedzenie w porządku, natomiast czystość i obsługa pozostawia dużo do życzenia",
        )

        val user = User("Kinga")

        for(i in restaurants.indices) {
            reviewList.add(Review(ratings[i], reviews[i], restaurants[i]!!, user))
        }

        binding.listReviews.adapter = ReviewsAdapter(this, reviewList)
    }
}
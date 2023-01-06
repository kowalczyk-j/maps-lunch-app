package pw.pap22z.bulionapp.ui.restaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.databinding.ActivityRestaurantBinding
import pw.pap22z.bulionapp.src.Restaurant
import pw.pap22z.bulionapp.src.Review
import pw.pap22z.bulionapp.src.User

class RestaurantActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRestaurantBinding
    private var reviewList: ArrayList<Review> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        put extra: restaurant id, then search for restaurant with such id
        val name = intent.getStringExtra("name")
        val address = intent.getStringExtra("address")
        val rating = intent.getStringExtra("rating")
        val menu = intent.getStringExtra("menu")

        binding.restaurantName.text = name
        binding.address.text = address
        binding.rating.text = rating
        binding.dishesText.text = menu

        val users = arrayOf(
            User("Kinga"),
            User("Abc123"),
            User("User1234"),
            User("bot"),
            User("AnotherUser")
        )

        val ratings = arrayOf(4.5, 4.0, 5.0, 4.0, 5.0)

        val reviews = arrayOf(
            "Bardzo smaczne jedzenie, świetna atmosfera. Będę polecać wszystkim znajomym :)",
            "Niedopieczony kurczak",
            "Wszystko w porządku",
            "Jedzenie w porządku, natomiast czystość i obsługa pozostawia dużo do życzenia",
            "Dla mnie wszystko świetnie, na najwyższym poziomie"
        )

        val restaurant = getDrawable(R.drawable.a)?.let {
            Restaurant("Aioli", "Świętokrzyska 18, Warszawa",
                it, "Lunch 3-daniowy: 29 zł", "12.00", "17.00"
            )
        }

        for(i in ratings.indices) {
            reviewList.add(Review(ratings[i], reviews[i], restaurant!!, users[i]))
        }

        binding.reviews.adapter = ReviewsAdapter(this, reviewList)

        val addReviewBtn: Button = findViewById<Button>(R.id.addReview)
        addReviewBtn.setOnClickListener{
            val intent = Intent(this, WriteReview::class.java)
            this?.startActivity(intent)
        }

    }

}
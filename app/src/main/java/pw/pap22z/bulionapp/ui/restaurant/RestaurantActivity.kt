package pw.pap22z.bulionapp.ui.restaurant

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.databinding.ActivityRestaurantBinding
import pw.pap22z.bulionapp.src.Restaurant
import pw.pap22z.bulionapp.src.Review
import pw.pap22z.bulionapp.src.User
import java.io.Serializable

class RestaurantActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRestaurantBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurant: Restaurant = intent.extras!!.get("restaurant") as Restaurant

        binding.restaurantName.text = restaurant.name
        binding.address.text = restaurant.address
        binding.rating.text = restaurant.rating.toString()
        binding.dishesText.text = restaurant.menu
        binding.restaurantLogo.setImageURI(Uri.parse(restaurant.logoUriStr))

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

        for(i in ratings.indices) {
            restaurant.reviews.add(Review(ratings[i], reviews[i], restaurant, users[i]))
        }

        binding.reviews.adapter = ReviewsAdapter(this, restaurant.reviews)

        val addReviewBtn: Button = findViewById<Button>(R.id.addReview)
        addReviewBtn.setOnClickListener{
            val intent = Intent(this, WriteReview::class.java)
            intent.putExtra("restaurant", restaurant as Serializable)
            this.startActivity(intent)
        }

    }

}
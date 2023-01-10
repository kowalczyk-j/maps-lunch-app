package pw.pap22z.bulionapp.ui.restaurant

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.data.entities.Restaurant
import pw.pap22z.bulionapp.databinding.ActivityRestaurantBinding
import pw.pap22z.bulionapp.src.Review
import pw.pap22z.bulionapp.src.User
import pw.pap22z.bulionapp.ui.profile.RestaurantReviewsAdapter
import java.io.Serializable

class RestaurantActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRestaurantBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val restaurant: Restaurant? = intent.getParcelableExtra("restaurant")
        val restaurant = intent.getParcelableExtra("restaurant", Restaurant::class.java)

        binding.restaurantName.text = restaurant!!.name
        binding.address.text = restaurant.address
        binding.dishesText.text = restaurant.lunch_info
        binding.restaurantLogo.setImageBitmap(restaurant.titleImage)
        binding.rating.text = restaurant.rating.toString()

        val users = arrayOf(
            User("Kinga"),
            User("Abc123"),
            User("User1234"),
            User("bot"),
            User("AnotherUser")
        )

        val ratings = arrayOf(4.5, 4.0, 5.0, 4.0, 5.0)

        val reviewBodys = arrayOf(
            "Bardzo smaczne jedzenie, świetna atmosfera. Będę polecać wszystkim znajomym :)",
            "Niedopieczony kurczak",
            "Wszystko w porządku",
            "Jedzenie w porządku, natomiast czystość i obsługa pozostawia dużo do życzenia",
            "Dla mnie wszystko świetnie, na najwyższym poziomie"
        )

        val reviews = arrayListOf<Review>()

        val temp_restaurant = pw.pap22z.bulionapp.src.Restaurant("Aioli",
            "Świętokrzyska 18, Warszawa",
            "Uri", "", "", "")

        for(i in ratings.indices) {
            reviews.add(Review(ratings[i], reviewBodys[i], temp_restaurant, users[i]))
        }

        val recyclerViewReviews: RecyclerView = findViewById(R.id.reviews)
        recyclerViewReviews.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewReviews.adapter = RestaurantReviewsAdapter(this, reviews)

        val addReviewBtn: Button = findViewById<Button>(R.id.addReview)
        addReviewBtn.setOnClickListener{
            val intent = Intent(this, WriteReview::class.java)
            intent.putExtra("restaurant", restaurant as Serializable)
            this.startActivity(intent)
        }

    }

}
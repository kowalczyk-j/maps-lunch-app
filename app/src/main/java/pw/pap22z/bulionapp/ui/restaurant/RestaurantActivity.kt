package pw.pap22z.bulionapp.ui.restaurant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pw.pap22z.bulionapp.data.RestaurantDatabase
import pw.pap22z.bulionapp.data.entities.Restaurant
import pw.pap22z.bulionapp.databinding.ActivityRestaurantBinding
import pw.pap22z.bulionapp.src.Review
import pw.pap22z.bulionapp.src.User

class RestaurantActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRestaurantBinding
    private var reviewList: ArrayList<Review> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val name = intent.getStringExtra("name")
//        val address = intent.getStringExtra("address")
//        val rating = intent.getStringExtra("rating")

        val restaurant = intent.getParcelableExtra("restaurant", Restaurant::class.java)
        //val lunch = intent.getParcelableExtra("lunch", Lunch::class.java)

        CoroutineScope(Dispatchers.Main).launch {
            val ldao by lazy { RestaurantDatabase.getDatabase(application).lunchDao() }
            val lunch = ldao.getLunchWithRestaurant(restaurant!!.restaurant_id)
            binding.address.text = lunch!!.lunch_body
        }
        binding.restaurantName.text = restaurant!!.titleImage

//        //binding.rating.text = restaurant.restaurant_id.toString()
//

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

        //val restaurant11 = Restaurant("Aioli", "Swietokrzyska 18, Warszawa")

        for(i in ratings.indices) {
            reviewList.add(Review(ratings[i], reviews[i], users[i]))
        }

        binding.reviews.adapter = ReviewsAdapter(this, reviewList)
    }
}
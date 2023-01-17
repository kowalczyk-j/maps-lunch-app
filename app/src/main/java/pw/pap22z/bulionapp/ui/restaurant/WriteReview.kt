package pw.pap22z.bulionapp.ui.restaurant

import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.data.entities.Restaurant
import pw.pap22z.bulionapp.data.entities.Review
import pw.pap22z.bulionapp.data.entities.User
import pw.pap22z.bulionapp.databinding.ActivityWriteReviewBinding
import pw.pap22z.bulionapp.ui.profile.ProfileViewModel
import pw.pap22z.bulionapp.ui.profile.RestaurantReviewsAdapter

class WriteReview : AppCompatActivity() {

    private lateinit var binding: ActivityWriteReviewBinding
    private lateinit var viewModel: RestaurantViewModel
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var user: User

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurant = intent.getParcelableExtra("restaurant", Restaurant::class.java)

        findViewById<TextView>(R.id.reviewHeader).text = "Jak oceniasz lunch w ${restaurant?.name}?"

        val reviewsAdapter = RestaurantReviewsAdapter(this)

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(
            RestaurantViewModel::class.java)
        viewModel.getRestaurantReviews(restaurant!!.restaurant_id)
        viewModel.reviews.observe(this, Observer {list -> list?.let {reviewsAdapter.setData(it)}})

        profileViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ProfileViewModel::class.java)

        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val reviewBody = findViewById<EditText>(R.id.review_body).text
        val addBtn: Button = findViewById<Button>(R.id.add)

        val retrieveUser = CoroutineScope(Dispatchers.IO).launch {
            user = profileViewModel.getUser(1)
        }

        runBlocking {
            retrieveUser.join() // wait until child coroutine completes
        }

        addBtn.setOnClickListener{
            if (ratingBar != null) {
                val rating: Float = ratingBar.rating
                Toast.makeText(this, "Dodano recenzję", Toast.LENGTH_SHORT).show()
                val addReviewThread = CoroutineScope(Dispatchers.IO).launch {
                    viewModel.insertReview(Review(
                        review_rating=rating,
                        review_body = reviewBody.toString(),
                        restaurant = restaurant,
                        user = user
                    ))

                }

                runBlocking {
                    addReviewThread.join() // wait until child coroutine completes
                }
            }
            finish()
        }
    }
}
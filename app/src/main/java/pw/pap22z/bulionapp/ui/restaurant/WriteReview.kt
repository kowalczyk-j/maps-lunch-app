package pw.pap22z.bulionapp.ui.restaurant

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurant = intent.getParcelableExtra("restaurant", Restaurant::class.java)

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

        val ratings = listOf(1, 2, 3, 4, 5)
        val adapter = ArrayAdapter(this, R.layout.rating_list_item, ratings)
        binding.dropdownField.setAdapter(adapter)

        var rating: Int? = null
        binding.dropdownField.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            rating = adapter.getItem(i)
        }

        val reviewBody = findViewById<EditText>(R.id.review_body).text

        val addBtn: Button = findViewById<Button>(R.id.add)

        val retrieveUser = CoroutineScope(Dispatchers.IO).launch {
            user = profileViewModel.getUser(1)
        }

        runBlocking {
            retrieveUser.join() // wait until child coroutine completes
        }

        addBtn.setOnClickListener{
            if (rating != null) {
                Toast.makeText(this, "Dodano recenzję", Toast.LENGTH_SHORT).show()
                lifecycleScope.launch {
                    viewModel.insertReview(Review(
                        review_rating=rating!!,
                        review_body = reviewBody.toString(),
                        restaurant = restaurant!!,
                        user = user
                    ))
                    val new_rating = "%.2f".format(viewModel.getRestaurantRating(restaurant!!.restaurant_id))
                    Log.d("TAG", "rating: $new_rating")
                    //Toast.makeText(context, "New rating: $new_rating", Toast.LENGTH_SHORT).show()
                    viewModel.updateRating(restaurant!!.restaurant_id, new_rating.toFloat())
                }



            }
            finish()
        }
    }
}
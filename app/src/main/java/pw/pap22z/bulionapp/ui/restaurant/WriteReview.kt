package pw.pap22z.bulionapp.ui.restaurant

import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.data.entities.Restaurant
import pw.pap22z.bulionapp.data.entities.Review
import pw.pap22z.bulionapp.data.entities.User
import pw.pap22z.bulionapp.databinding.ActivityWriteReviewBinding
import pw.pap22z.bulionapp.ui.profile.ProfileViewModel

class WriteReview : AppCompatActivity(), RestaurantReviewsAdapter.OnDeleteClickListener {

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

        findViewById<TextView>(R.id.reviewHeader).text = getString(R.string.review_header, restaurant!!.name)

        val reviewsAdapter = RestaurantReviewsAdapter(this, this)

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application))[RestaurantViewModel::class.java]
        viewModel.getRestaurantReviews(restaurant.restaurant_id)
        viewModel.reviews.observe(this) { list -> list?.let { reviewsAdapter.setData(it) } }

        profileViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[ProfileViewModel::class.java]

        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val reviewBody = findViewById<EditText>(R.id.review_body).text
        val addBtn: Button = findViewById(R.id.add)

        val retrieveUser = CoroutineScope(Dispatchers.IO).launch {
            user = profileViewModel.getUser(1)
        }

        runBlocking {
            retrieveUser.join() // wait until child coroutine completes
        }

        addBtn.setOnClickListener {
            if (ratingBar != null) {
                val rating: Float = ratingBar.rating
                val reviewText = reviewBody.toString()
                val validationResult = validateReview(rating, reviewText)
                if (validationResult == ReviewValidationResult.VALID) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val existingReview = viewModel.getReviewByUserAndRestaurant(user.user_id, restaurant.restaurant_id)
                        withContext(Dispatchers.Main) {
                            if (existingReview != null) {
                                Toast.makeText(this@WriteReview, getString(R.string.error_review_exists), Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this@WriteReview, getString(R.string.added_review), Toast.LENGTH_SHORT).show()
                                viewModel.insertReview(
                                    Review(
                                        review_rating = rating,
                                        review_body = reviewText,
                                        restaurant = restaurant,
                                        user = user
                                    )
                                )
                                finish()
                            }
                        }
                    }
                } else {
                    val errorMessage = when (validationResult) {
                        ReviewValidationResult.INVALID_RATING -> getString(R.string.error_scale)
                        ReviewValidationResult.EMPTY_REVIEW -> getString(R.string.error_empty_review)
                        ReviewValidationResult.EXCEEDED_MAX_CHARACTERS -> getString(R.string.error_max_characters)
                        ReviewValidationResult.EXCEEDED_MAX_LINES -> getString(R.string.error_max_lines)
                        else -> {getString(R.string.added_review)}
                    }
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validateReview(rating: Float, reviewText: String): ReviewValidationResult {
        if (rating < 1) {
            return ReviewValidationResult.INVALID_RATING
        }
        if (reviewText.isEmpty()) {
            return ReviewValidationResult.EMPTY_REVIEW
        }
        if (reviewText.length > 1000) {
            return ReviewValidationResult.EXCEEDED_MAX_CHARACTERS
        }
        if (reviewText.lines().size > 25) {
            return ReviewValidationResult.EXCEEDED_MAX_LINES
        }
        return ReviewValidationResult.VALID
    }

    enum class ReviewValidationResult {
        VALID,
        INVALID_RATING,
        EMPTY_REVIEW,
        EXCEEDED_MAX_CHARACTERS,
        EXCEEDED_MAX_LINES
    }

    override fun onDeleteClick(review: Review) {
        val deleteReviewThread = CoroutineScope(Dispatchers.IO).launch {
            viewModel.deleteReview(review)
        }
        deleteReviewThread.invokeOnCompletion {
            finish()
        }
    }
}
package pw.pap22z.bulionapp.ui.restaurant

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.data.entities.Restaurant
import pw.pap22z.bulionapp.data.entities.Review
import pw.pap22z.bulionapp.data.entities.User
import pw.pap22z.bulionapp.databinding.ActivityWriteReviewBinding
import pw.pap22z.bulionapp.ui.profile.RestaurantReviewsAdapter

class WriteReview : AppCompatActivity() {

    private lateinit var binding: ActivityWriteReviewBinding
    private lateinit var viewModel: RestaurantViewModel

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



        val ratings = listOf(1, 2, 3, 4, 5)
        val adapter = ArrayAdapter(this, R.layout.rating_list_item, ratings)
        binding.dropdownField.setAdapter(adapter)

        var rating: Int? = null
        binding.dropdownField.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            rating = adapter.getItem(i)
        }

        val reviewBody = findViewById<EditText>(R.id.review_body).text

        val addBtn: Button = findViewById<Button>(R.id.add)

        addBtn.setOnClickListener{
            if (rating != null) {
                Toast.makeText(this, "Dodano recenzję", Toast.LENGTH_SHORT).show()
                lifecycleScope.launch {
                    viewModel.insertReview(Review(
                        review_rating=rating!!,
                        review_body = reviewBody.toString(),
                        restaurant = restaurant!!,
                        user = User(1, "Kinga", BitmapFactory.decodeResource(resources, R.drawable.profile1))
                    ))
                }
            }
            finish()
        }
    }
}
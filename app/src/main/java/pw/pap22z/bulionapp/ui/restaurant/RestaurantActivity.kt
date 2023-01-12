package pw.pap22z.bulionapp.ui.restaurant

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.data.entities.Restaurant
import pw.pap22z.bulionapp.databinding.ActivityRestaurantBinding
import pw.pap22z.bulionapp.ui.profile.RestaurantReviewsAdapter

class RestaurantActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRestaurantBinding
    private lateinit var viewModel: RestaurantViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurant = intent.getParcelableExtra("restaurant", Restaurant::class.java)

        val price = "%.2f".format(restaurant!!.price)

        binding.restaurantName.text = restaurant.name
        binding.address.text = restaurant.address
        binding.dishesText.text = restaurant.lunch_info
        binding.restaurantLogo.setImageBitmap(restaurant.titleImage)
        binding.rating.text = restaurant.rating.toString()
        binding.price.text = price
        binding.hours.text = "${restaurant.hour_start}.00-${restaurant.hour_end}.00"

        val adapter = RestaurantReviewsAdapter(this)

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(
            RestaurantViewModel::class.java)
        viewModel.getRestaurantReviews(restaurant.restaurant_id)
        viewModel.reviews.observe(this, Observer {list -> list?.let {adapter.setData(it)}})

        val recyclerViewReviews: RecyclerView = findViewById(R.id.reviews)
        recyclerViewReviews.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewReviews.adapter = adapter

        val addReviewBtn: Button = findViewById<Button>(R.id.addReview)
        addReviewBtn.setOnClickListener{
            val intent = Intent(this, WriteReview::class.java)
            intent.putExtra("restaurant", restaurant)
            this.startActivity(intent)
        }

        val updateLunchBtn: Button = findViewById<Button>(R.id.updateLunch)
        updateLunchBtn.setOnClickListener{
            val intent = Intent(this, UpdateLunch::class.java)
            intent.putExtra("restaurant", restaurant)
            this.startActivity(intent)
        }

        val openLinkButton: ImageButton = findViewById<ImageButton>(R.id.button_open_link)
        openLinkButton.setOnClickListener {
            val url = "https://www.facebook.com/AIOLICantineSwietokrzyska/photos/a.540039119386448/6038734066183565/"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

    }

}

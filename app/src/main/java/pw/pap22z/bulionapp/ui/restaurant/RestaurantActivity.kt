package pw.pap22z.bulionapp.ui.restaurant

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.data.entities.Restaurant
import pw.pap22z.bulionapp.databinding.ActivityRestaurantBinding
import pw.pap22z.bulionapp.ui.profile.RestaurantReviewsAdapter

class RestaurantActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRestaurantBinding
    private lateinit var viewModel: RestaurantViewModel
    private lateinit var restaurant: Restaurant
    private lateinit var favoritesBtn: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(
            RestaurantViewModel::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            restaurant = intent.getParcelableExtra("restaurant", Restaurant::class.java)!!
        }
        else {
            restaurant = intent.getParcelableExtra<Restaurant>("restaurant")!!
        }

        favoritesBtn = findViewById<ImageButton>(R.id.favoritesBtn)

        val price = "%.2f".format(restaurant!!.price)

        var new_rating: Float? = null

        val retrieveNewRating = CoroutineScope(Dispatchers.IO).launch {
            new_rating = viewModel.getRestaurantRating(restaurant!!.restaurant_id)
            Log.d("log", "new rating: $new_rating" )
            //Toast.makeText(context, "New rating: $new_rating", Toast.LENGTH_SHORT).show()
        }

        runBlocking {
            retrieveNewRating.join() // wait until child coroutine completes
        }

        val updateRatingThread = CoroutineScope(Dispatchers.IO).launch {
            if (new_rating != null) {
                viewModel.updateRating(restaurant!!.restaurant_id, new_rating!!)
            }
            else { viewModel.updateRating(restaurant!!.restaurant_id, 0.0f) }
        }

        runBlocking {
            updateRatingThread.join() // wait until child coroutine completes
        }

        binding.restaurantName.text = restaurant.name
        binding.address.text = restaurant.address
        binding.dishesText.text = restaurant.lunch_info
        binding.restaurantLogo.setImageBitmap(restaurant.titleImage)
        binding.rating.text = "%.2f".format(restaurant.rating)
        binding.price.text = "Cena za zestaw lunchowy: $price"
        binding.hours.text = "${restaurant.hour_start}.00-${restaurant.hour_end}.00"
        binding.editInfo.text = "Edytowano: ${restaurant.edit_date}"

        val adapter = RestaurantReviewsAdapter(this)


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
        val user = runBlocking { viewModel.getUser(1) }
        val isAdmin = user.is_admin
        if ( isAdmin ) {
            updateLunchBtn.visibility = View.VISIBLE
        } else {
            updateLunchBtn.visibility = View.GONE
        }

        updateLunchBtn.setOnClickListener{
            val intent = Intent(this, UpdateLunch::class.java)
            intent.putExtra("restaurant", restaurant)
            this.startActivity(intent)
        }

        val openLinkButton: ImageButton = findViewById<ImageButton>(R.id.button_open_link)
        openLinkButton.setOnClickListener {
            val url = restaurant.menu
            if (url == ""){
                Toast.makeText(this, "Restauracja nie udostępniła menu", Toast.LENGTH_SHORT).show()
            }
            else {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }

        }

//        FAVORITES
        if (restaurant.favorite) { favoritesBtn.setImageResource(R.drawable.favorite) }
        else { favoritesBtn.setImageResource(R.drawable.not_favorite) }

        favoritesBtn.setOnClickListener {
            if (restaurant.favorite) {
                val thread = CoroutineScope(Dispatchers.IO).launch { viewModel.removeFromFavorites(restaurant.restaurant_id) }
                runBlocking { thread.join() }
                println("nie w ulubionych")
                favoritesBtn.setImageResource(R.drawable.not_favorite)
            } else {
                val thread = CoroutineScope(Dispatchers.IO).launch { viewModel.addToFavorites(restaurant.restaurant_id) }
                runBlocking { thread.join() }
                println("w ulubionych")
                favoritesBtn.setImageResource(R.drawable.favorite)
            }
            val thread = CoroutineScope(Dispatchers.IO).launch { restaurant = viewModel.getRestaurantById(restaurant.restaurant_id) }
            runBlocking { thread.join() }
        }
    }



}
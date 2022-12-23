package pw.pap22z.bulionapp.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pw.pap22z.bulionapp.databinding.ActivityMyFavoritesBinding
import pw.pap22z.bulionapp.src.Restaurant
import pw.pap22z.bulionapp.ui.restaurant.RestaurantActivity

class MyFavoritesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyFavoritesBinding
    private var favoritesList: ArrayList<Restaurant> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurants = arrayOf(
            Restaurant("Fast Food", "Świętokrzyska 18, Warszawa"),
            Restaurant("Caffe", "Skarbka z Gór 51, Warszawa"),
            Restaurant("Pizzeria", "Danusi 10, Warszawa")
        )

        val ratings = arrayOf(5.0, 4.9, 4.7)

        for(i in restaurants.indices) {
            restaurants[i].rating = ratings[i]
            favoritesList.add(restaurants[i])
        }

        binding.favoritesList.isClickable = true

        binding.favoritesList.adapter = FavoritesAdapter(this, favoritesList)

        binding.favoritesList.setOnItemClickListener {parent, view, position, id ->
            val restaurant = favoritesList[position]
            val intent = Intent(this, RestaurantActivity::class.java)

            intent.putExtra("name", restaurant.name)
            intent.putExtra("address", restaurant.address)
            intent.putExtra("rating", restaurant.rating.toString())
//            intent.putExtra("reviews", restaurant.reviews)

            startActivity(intent)

        }
    }
}
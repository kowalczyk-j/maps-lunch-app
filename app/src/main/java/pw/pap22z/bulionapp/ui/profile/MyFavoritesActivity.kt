package pw.pap22z.bulionapp.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pw.pap22z.bulionapp.R
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
            getDrawable(R.drawable.a)?.let {
                Restaurant("Aioli", "Świętokrzyska 18, Warszawa",
                    it, "Lunch 3-daniowy: 29 zł", "12.00", "17.00"
                )
            },
            getDrawable(R.drawable.b)?.let {
                Restaurant("Bordo Bistro", "Świętokrzyska 18, Warszawa",
                    it, "Lunch 3-daniowy: 23 zł", "12.00", "15.00"
                )
            },getDrawable(R.drawable.c)?.let {
                Restaurant("Marcello", "Świętokrzyska 18, Warszawa",
                    it, "Danie dnia + napój: 29 zł", "15.00", "17.00"
                )
            },
        )

        val ratings = arrayOf(5.0, 4.9, 4.7)

        for(i in restaurants.indices) {
            restaurants[i]!!.rating = ratings[i]
            favoritesList.add(restaurants[i]!!)
        }

        binding.favoritesList.isClickable = true

        binding.favoritesList.adapter = FavoritesAdapter(this, favoritesList)

        binding.favoritesList.setOnItemClickListener {parent, view, position, id ->
            val restaurant = favoritesList[position]
            val intent = Intent(this, RestaurantActivity::class.java)

            intent.putExtra("name", restaurant.name)
            intent.putExtra("address", restaurant.address)
            intent.putExtra("rating", restaurant.rating.toString())
            intent.putExtra("menu", restaurant.menu)
//            intent.putExtra("reviews", restaurant.reviews)

            startActivity(intent)

        }
    }
}
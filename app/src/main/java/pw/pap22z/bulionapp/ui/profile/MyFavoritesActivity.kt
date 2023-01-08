package pw.pap22z.bulionapp.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.databinding.ActivityMyFavoritesBinding
import pw.pap22z.bulionapp.src.Restaurant
import pw.pap22z.bulionapp.ui.restaurant.RestaurantActivity
import java.io.Serializable

class MyFavoritesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyFavoritesBinding
    private var favoritesList: ArrayList<Restaurant> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurants = arrayOf(
            Restaurant("Aioli", "Świętokrzyska 18, Warszawa", "android.resource://pw.pap22z.bulionapp/drawable/a",
                "Lunch 3-daniowy: 29 zł", "12.00", "17.00"
            ),
            Restaurant("Bordo Bistro", "Świętokrzyska 18, Warszawa", "android.resource://pw.pap22z.bulionapp/drawable/b",
                    "Lunch 3-daniowy: 23 zł", "12.00", "15.00"
            ),
            Restaurant("Marcello", "Świętokrzyska 18, Warszawa", "android.resource://pw.pap22z.bulionapp/drawable/c",
                "Danie dnia + napój: 29 zł", "15.00", "17.00"
            )
        )

        val ratings = arrayOf(5.0, 4.9, 4.7)

        for(i in restaurants.indices) {
            restaurants[i].rating = ratings[i]
            favoritesList.add(restaurants[i])
        }

        val recyclerViewFavorites: RecyclerView = findViewById(R.id.favorites_list)
        val adapter: FavoritesAdapter = FavoritesAdapter(this, favoritesList)
        val context = this
        recyclerViewFavorites.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewFavorites.adapter = adapter
        adapter.setOnItemClickListener(object: FavoritesAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(context, RestaurantActivity::class.java)
                intent.putExtra("restaurant", favoritesList[position] as Serializable)
                startActivity(intent)
            }

        })

    }
}
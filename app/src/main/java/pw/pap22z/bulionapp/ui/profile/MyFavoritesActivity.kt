package pw.pap22z.bulionapp.ui.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pw.pap22z.bulionapp.databinding.ActivityMyFavoritesBinding
import pw.pap22z.bulionapp.src.Restaurant

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

        binding.favoritesList.adapter = FavoritesAdapter(this, favoritesList)
    }
}
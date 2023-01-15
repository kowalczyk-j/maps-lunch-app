package pw.pap22z.bulionapp.ui.profile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.launch
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.data.entities.Restaurant
import pw.pap22z.bulionapp.data.entities.Review
import pw.pap22z.bulionapp.data.entities.User
import pw.pap22z.bulionapp.databinding.ActivityMyReviewsBinding


class MyReviewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyReviewsBinding
    private lateinit var viewModel: MyReviewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyReviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ReviewsAdapter(this)

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(MyReviewsViewModel::class.java)
        viewModel.userReviews.observe(this, Observer {list -> list?.let {adapter.setData(it)}})

//        var reviewList: ArrayList<Review> = arrayListOf()
//
//        val restaurants = arrayOf(
//            Restaurant("Aioli", "Świętokrzyska 18, Warszawa", "android.resource://pw.pap22z.bulionapp/drawable/a",
//                "Lunch 3-daniowy: 29 zł", "12.00", "17.00"
//            ),
//            Restaurant("Bordo Bistro", "Świętokrzyska 18, Warszawa", "android.resource://pw.pap22z.bulionapp/drawable/b",
//                "Lunch 3-daniowy: 23 zł", "12.00", "15.00"
//            ),
//            Restaurant("Marcello", "Świętokrzyska 18, Warszawa", "android.resource://pw.pap22z.bulionapp/drawable/c",
//                "Danie dnia + napój: 29 zł", "15.00", "17.00"
//            )
//        )
//
//        val ratings = arrayOf(
//            5.0,
//            3.5,
//            4.5
//        )
//
//        val reviews = arrayOf(
//            "Bardzo smaczne jedzenie, świetna atmosfera. Będę polecać wszystkim znajomym :)",
//            "Wszystko w porządku",
//            "Jedzenie w porządku, natomiast czystość i obsługa pozostawia dużo do życzenia",
//        )
//
//        val user = User("Kinga")
//
//        for(i in restaurants.indices) {
//            reviewList.add(Review(ratings[i], reviews[i], restaurants[i], user))
//        }

        lifecycleScope.launch {
            viewModel.insertReview(
                Review(
                    1,
                    5.0f,
                    "Bardzo smaczne jedzenie, świetna atmosfera. Będę polecać wszystkim znajomym :)",
                Restaurant(1, getBitmap("https://sztuczne-rosliny.pl/wp-content/uploads/2020/01/aioli-logo.jpg"),
                    "Aioli", 4.5F, "Włoska", "Chmielna 26", 26.90F, 12, 17, 3, true,
                    "Zupa: zupa z zielonym groszkiem\n 1 z 3 dań głównych do wyboru: \n1. Chutney Jalapeno Burger \n2. Tuna Salad \n3. Pizza Chorizo\n Minideser: Budyń "),
                User(
                    1,
                    "Kinga",
                    BitmapFactory.decodeResource(resources, R.drawable.profile1)
                )
                )
            )
        }
        val recyclerViewReviews: RecyclerView = findViewById(R.id.listReviews)
        recyclerViewReviews.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewReviews.adapter = adapter
    }


    private  suspend fun getBitmap(url: String): Bitmap {
        val loading = ImageLoader(this)
        val request = ImageRequest.Builder(this)
            .data(url)
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

}
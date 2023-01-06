package pw.pap22z.bulionapp.ui.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pw.pap22z.bulionapp.data.entities.Review
import pw.pap22z.bulionapp.databinding.ActivityMyReviewsBinding

class MyReviewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyReviewsBinding
    private lateinit var reviewList: List<Review>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyReviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val restaurants = arrayOf(
//            Restaurant("Fast Food", "Warszawa"),
//            Restaurant("Chińczyk", "Warszawa"),
//            Restaurant("Pizzeria", "Warszawa"),
//            Restaurant("Caffe", "Warszawa"),
//            Restaurant("Kebab", "Warszawa")
//        )
//
//        val ratings = arrayOf(
//            5.0,
//            3.5,
//            4.5,
//            3.5,
//            3.0
//        )
//
//        val reviews = arrayOf(
//            "Bardzo smaczne jedzenie, świetna atmosfera. Będę polecać wszystkim znajomym :)",
//            "Niedopieczony kurczak",
//            "Wszystko w porządku",
//            "Jedzenie w porządku, natomiast czystość i obsługa pozostawia dużo do życzenia",
//            "Otrzymałam nie to co zamawiałam"
//        )
//
//        val user = User("Kinga")
//
//        for(i in restaurants.indices) {
//            reviewList.add(Review(ratings[i], reviews[i], user))
//        }

        //to powinno być w ReviewsViewModel
//        val dao = RestaurantDatabase.getDatabase(application).restaurantDao()
//        val repository = Repository(dao)
        //reviewList = repository.readReviews()
//        GlobalScope.launch(Dispatchers.IO) {
//            repository.insertReview(Review("Do Aioli", 0))
//        }
        reviewList = emptyList()
        binding.listReviews.adapter = ReviewsAdapter(this, reviewList)



    }
}
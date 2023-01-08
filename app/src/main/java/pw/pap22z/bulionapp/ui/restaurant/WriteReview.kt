package pw.pap22z.bulionapp.ui.restaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.databinding.ActivityWriteReviewBinding
import pw.pap22z.bulionapp.src.Restaurant
import pw.pap22z.bulionapp.src.Review
import pw.pap22z.bulionapp.src.User

class WriteReview : AppCompatActivity() {

    private lateinit var binding: ActivityWriteReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurant: Restaurant = intent.extras!!.get("restaurant") as Restaurant

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
                restaurant.reviews.add(Review(rating!!.toDouble(), reviewBody.toString(), restaurant, User("Kinga")))
            }
            finish()
        }
    }
}
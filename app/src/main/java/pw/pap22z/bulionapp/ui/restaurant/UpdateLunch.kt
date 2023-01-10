package pw.pap22z.bulionapp.ui.restaurant

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.data.RestaurantDatabase
import pw.pap22z.bulionapp.data.entities.Restaurant
import pw.pap22z.bulionapp.databinding.ActivityUpdateLunchBinding

class UpdateLunch : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateLunchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateLunchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurant = intent.getParcelableExtra("restaurant", Restaurant::class.java)

        val dao by lazy { RestaurantDatabase.getDatabase(application).restaurantDao() }


        val addBtn: Button = findViewById(R.id.update_btn)
        addBtn.setOnClickListener{
                Toast.makeText(this, "Pomyślnie zaktualizowano lunch", Toast.LENGTH_SHORT).show()
            }
            //finish()
        }
}
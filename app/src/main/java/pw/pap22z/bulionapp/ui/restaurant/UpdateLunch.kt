package pw.pap22z.bulionapp.ui.restaurant

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.data.RestaurantDatabase
import pw.pap22z.bulionapp.data.entities.Restaurant
import pw.pap22z.bulionapp.databinding.ActivityUpdateLunchBinding
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

class UpdateLunch : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateLunchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateLunchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurant = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("restaurant", Restaurant::class.java)
        } else {
            intent.getParcelableExtra<Restaurant>("restaurant")
        }
        val dao by lazy { RestaurantDatabase.getDatabase(application).restaurantDao() }


        val addBtn: Button = findViewById(R.id.update_btn)
        addBtn.setOnClickListener{
                val starter: EditText = findViewById(R.id.updateStarter)
                val mainDish : EditText = findViewById(R.id.updateMainDish)
                val dessert : EditText = findViewById(R.id.updateDessert)
                val info : EditText = findViewById(R.id.updateInfo)
                val menu : EditText = findViewById(R.id.updateMenu)
                val fields = arrayOf(starter, mainDish, dessert)
                val notNullFields = fields.count { it.text.toString().isNotEmpty() }
                val calendar = Calendar.getInstance()
                val current = SimpleDateFormat("yyyy-MM-dd HH:mm").format(calendar.time)
                val dayOfWeek = DateFormatSymbols(Locale("pl", "PL")).weekdays[calendar.get(Calendar.DAY_OF_WEEK)]

            if (restaurant!!.dishes_count < notNullFields ) {
                Toast.makeText(this, "Uzupełnij tylko odpowiednie pola!", Toast.LENGTH_SHORT).show()
            }
            else {
                var newLunch = ""
                if (starter.text.toString().isNotEmpty()) { newLunch += starter.text.toString() }
                if (mainDish.text.toString().isNotEmpty()) { newLunch = newLunch + "\n" + mainDish.text.toString() }
                if (dessert.text.toString().isNotEmpty()) { newLunch = newLunch + "\n" + dessert.text.toString() }
                if (info.text.toString().isNotEmpty()) { newLunch = newLunch + "\n" + info.text.toString() }
                if (menu.text.toString().isNotEmpty()) {
                    lifecycleScope.launch { dao.updateMenu(restaurant.restaurant_id, menu.text.toString()) }
                }
                lifecycleScope.launch { dao.updateLunch(restaurant!!.restaurant_id, newLunch)
                    dao.updateEditDate(restaurant.restaurant_id, "$dayOfWeek, $current") }
                Toast.makeText(this, "Pomyślnie zaktualizowano lunch", Toast.LENGTH_SHORT).show()
                finish()
            }


            }

        }
}
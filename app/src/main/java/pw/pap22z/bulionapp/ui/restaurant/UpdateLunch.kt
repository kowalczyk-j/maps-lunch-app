package pw.pap22z.bulionapp.ui.restaurant

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.data.RestaurantDao
import pw.pap22z.bulionapp.data.RestaurantDatabase
import pw.pap22z.bulionapp.data.entities.Restaurant
import pw.pap22z.bulionapp.databinding.ActivityUpdateLunchBinding
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class UpdateLunch : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateLunchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateLunchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurant = intent.getParcelableExtra<Restaurant>("restaurant")
        val dao by lazy { RestaurantDatabase.getDatabase(application).restaurantDao() }

        setupUpdateButton(restaurant, dao)
    }

    private fun setupUpdateButton(restaurant: Restaurant?, dao: RestaurantDao) {
        val addBtn: Button = findViewById(R.id.update_btn)
        addBtn.setOnClickListener {
            val starter: EditText = findViewById(R.id.updateStarter)
            val mainDish: EditText = findViewById(R.id.updateMainDish)
            val dessert: EditText = findViewById(R.id.updateDessert)
            val info: EditText = findViewById(R.id.updateInfo)
            val menu: EditText = findViewById(R.id.updateMenu)
            val fields = arrayOf(starter, mainDish, dessert)
            val notNullFields = fields.count { it.text.toString().isNotEmpty() }
            val calendar = Calendar.getInstance()
            val current = formatDateForLocale(calendar.time)
            val dayOfWeek = DateFormatSymbols(Locale("pl", "PL")).weekdays[calendar.get(Calendar.DAY_OF_WEEK)]

            if ((restaurant?.dishes_count ?: 0) < notNullFields) {
                Toast.makeText(this, getString(R.string.update_lunch_message), Toast.LENGTH_SHORT).show()
            } else {
                var newLunch = ""
                if (starter.text.toString().isNotEmpty()) { newLunch += starter.text.toString() }
                if (mainDish.text.toString().isNotEmpty()) { newLunch = newLunch + "\n" + mainDish.text.toString() }
                if (dessert.text.toString().isNotEmpty()) { newLunch = newLunch + "\n" + dessert.text.toString() }
                if (info.text.toString().isNotEmpty()) { newLunch = newLunch + "\n" + info.text.toString() }
                if (menu.text.toString().isNotEmpty()) {
                    lifecycleScope.launch { dao.updateMenu(restaurant!!.restaurant_id, menu.text.toString()) }
                }
                lifecycleScope.launch {
                    dao.updateLunch(restaurant!!.restaurant_id, newLunch)
                    dao.updateEditDate(restaurant.restaurant_id, "$dayOfWeek, $current")
                }
                Toast.makeText(this, getString(R.string.updated_lunch_message), Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun formatDateForLocale(date: Date): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
        return formatter.format(date)
    }
}
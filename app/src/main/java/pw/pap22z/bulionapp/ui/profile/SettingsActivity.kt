package pw.pap22z.bulionapp.ui.profile

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.data.entities.User
import pw.pap22z.bulionapp.ui.restaurant.RestaurantViewModel


class SettingsActivity : AppCompatActivity() {

    private lateinit var profilePicture: ImageView
    private lateinit var viewModel: ProfileViewModel
    private lateinit var restaurantViewModel: RestaurantViewModel
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val setUsernameBtn: Button = findViewById(R.id.setUsername)
        val setAvatarBtn: Button = findViewById(R.id.setAvatar)

        val profileLayout = layoutInflater.inflate(R.layout.fragment_profile, null)
        profilePicture = profileLayout.findViewById(R.id.avatar)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[ProfileViewModel::class.java]

        restaurantViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[RestaurantViewModel::class.java]

        val retrieveUser = CoroutineScope(Dispatchers.IO).launch {
            user = viewModel.getUser(1)
        }

        runBlocking {
            retrieveUser.join() // wait until child coroutine completes
        }

        setUsernameBtn.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            val dialogLayout = layoutInflater.inflate(R.layout.edit_username, null)
            val editText = dialogLayout.findViewById<EditText>(R.id.editTextUsername)

            with(builder) {
                setTitle(getString(R.string.change_username))
                setPositiveButton("Ok") { _, _ ->
                    if (editText.text.toString() != "") {
                        lifecycleScope.launch {
                            val newUsername: String = editText.text.toString()
                            viewModel.updateUsername(user.user_id, newUsername)
                            restaurantViewModel.updateReviewUsername(user.user_id, newUsername)
                        }
                    }
                }
                setNegativeButton(getString(R.string.cancel_username)) { _, _ ->
                    Log.d("Settings", "Negative button clicked")
                }
                setView(dialogLayout)
                show()
            }
        }

        setAvatarBtn.setOnClickListener{
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).also {
                it.addCategory(Intent.CATEGORY_OPENABLE)
                it.type = "image/*"
                it.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
                it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            startActivityForResult(intent, 3)
        }

        val adminSwitch: Switch = findViewById(R.id.adminSwitch)

        lifecycleScope.launch {
            val user = viewModel.getUser(1)
            adminSwitch.isChecked = user.is_admin
            adminSwitch.setOnCheckedChangeListener { _, isChecked -> viewModel.updateAdminInfo(1, isChecked) }
        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            contentResolver.takePersistableUriPermission(data.data!!, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            lifecycleScope.launch {
                val newProfilePic: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, data.data)
                viewModel.updateProfilePic(user.user_id, newProfilePic)
                restaurantViewModel.updateReviewProfilePic(user.user_id, newProfilePic)
        }
        }
    }

}

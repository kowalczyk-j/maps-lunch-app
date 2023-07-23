package pw.pap22z.bulionapp.ui.profile

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
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
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var restaurantViewModel: RestaurantViewModel
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val setUsernameBtn: Button = findViewById(R.id.setUsername)
        val setAvatarBtn: Button = findViewById(R.id.setAvatar)

        val profileLayout = layoutInflater.inflate(R.layout.fragment_profile, null)
        profilePicture = profileLayout.findViewById(R.id.avatar)

        profileViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[ProfileViewModel::class.java]

        restaurantViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[RestaurantViewModel::class.java]

        val retrieveUser = CoroutineScope(Dispatchers.IO).launch {
            user = profileViewModel.getUser(1)
        }

        runBlocking {
            retrieveUser.join() // wait until child coroutine completes
        }

        setUsernameBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val dialogLayout = layoutInflater.inflate(R.layout.dialog_edit_username, null)
            val editText = dialogLayout.findViewById<EditText>(R.id.editTextUsername)

            with(builder) {
                setTitle(getString(R.string.change_username))
                setPositiveButton("Ok") { _, _ ->
                    val newUsername: String = editText.text.toString()
                    if (newUsername.isNotEmpty() && newUsername.length <= 14) {
                        lifecycleScope.launch {
                            profileViewModel.updateUsername(user.user_id, newUsername)
                            restaurantViewModel.updateReviewUsername(user.user_id, newUsername)
                        }
                    } else {
                        Toast.makeText(this@SettingsActivity, getString(R.string.empty_username), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                setNegativeButton(getString(R.string.cancel_username)) { _, _ ->
                    Log.d("Settings", "Negative button clicked")
                }
                setView(dialogLayout)
                show()
            }
        }

        setAvatarBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).also {
                it.addCategory(Intent.CATEGORY_OPENABLE)
                it.type = "image/*"
                it.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
                it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            startActivityForResult(intent, 3)
        }

        val adminSwitch: SwitchCompat = findViewById(R.id.adminSwitch)

        lifecycleScope.launch {
            val user = profileViewModel.getUser(1)
            adminSwitch.isChecked = user.is_admin
            adminSwitch.setOnCheckedChangeListener { _, isChecked -> profileViewModel.updateAdminInfo(1, isChecked) }
        }
    }

    @Deprecated("This method is deprecated")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            contentResolver.takePersistableUriPermission(data.data!!, Intent.FLAG_GRANT_READ_URI_PERMISSION)

            val fileSizeValid = isImageSizeValid(data.data!!)
            if (!fileSizeValid) {
                Toast.makeText(this, R.string.error_photo_size, Toast.LENGTH_SHORT).show()
                return
            }

            lifecycleScope.launch {
                val newProfilePic: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, data.data)
                profileViewModel.updateProfilePic(user.user_id, newProfilePic)
                restaurantViewModel.updateReviewProfilePic(user.user_id, newProfilePic)
            }
        }
    }

    private fun isImageSizeValid(uri: Uri): Boolean {
        val fileDescriptor = contentResolver.openFileDescriptor(uri, "r")
        val fileSizeInBytes = fileDescriptor?.statSize ?: 0
        val fileSizeInMB = fileSizeInBytes / (1024.0 * 1024.0)
        fileDescriptor?.close()
        return fileSizeInMB <= 0.2
    }
}
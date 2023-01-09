package pw.pap22z.bulionapp.ui.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import pw.pap22z.bulionapp.R


class SettingsActivity : AppCompatActivity() {

    lateinit var profilePicture: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val setUsernameBtn: Button = findViewById(R.id.setUsername)
        val setAvatarBtn: Button = findViewById(R.id.setAvatar)

        val profileLayout = layoutInflater.inflate(R.layout.fragment_profile, null)
        profilePicture = profileLayout.findViewById(R.id.avatar)

        setUsernameBtn.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            val dialogLayout = layoutInflater.inflate(R.layout.edit_username, null)
            val editText = dialogLayout.findViewById<EditText>(R.id.editTextUsername)

            with(builder) {
                setTitle("Zmień nazwę użytkownika")
                setPositiveButton("Ok") {dialog, which ->
                    if (editText.text.toString() != "") {
                        user.username = editText.text.toString()
                    }
                }
                setNegativeButton("Anuluj") {dialog, which ->
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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            contentResolver.takePersistableUriPermission(data.data!!, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            user.profilePicture = MediaStore.Images.Media.getBitmap(contentResolver, data.data)
        }
    }

}

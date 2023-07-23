package pw.pap22z.bulionapp.ui.profile

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pw.pap22z.bulionapp.data.RestaurantDatabase
import pw.pap22z.bulionapp.data.entities.User

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao by lazy { RestaurantDatabase.getDatabase(application).userDao() }

    fun updateUsername(userId: Int, newUsername: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userDao.updateUsername(userId, newUsername)
        }
    }

    fun updateProfilePic(userId: Int, newPic: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            userDao.updateProfilePic(userId, newPic)
        }
    }

    suspend fun getUser(userId: Int): User {
        return userDao.getUser(userId)
    }

    fun updateAdminInfo(id: Int, is_admin: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            userDao.updateAdminInfo(id, is_admin)
        }
    }

}
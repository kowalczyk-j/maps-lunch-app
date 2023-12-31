package pw.pap22z.bulionapp.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBindings
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.data.entities.User
import pw.pap22z.bulionapp.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private lateinit var user: User
    private lateinit var profileViewModel: ProfileViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[ProfileViewModel::class.java]

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val retrieveUser = CoroutineScope(Dispatchers.IO).launch {
            user = profileViewModel.getUser(1)
        }

        runBlocking {
            retrieveUser.join() // wait until child coroutine completes
        }


        val welcomeMsg: TextView = binding.textWelcome
        welcomeMsg.text = user.username

        /* REVIEWS BUTTON */
        ViewBindings.findChildViewById<Button>(root, R.id.reviewsBtn)?.setOnClickListener {
            val intent = Intent(activity, MyReviewsActivity::class.java)
            activity?.startActivity(intent)
        }

        /* FAVORITES BUTTON */
        ViewBindings.findChildViewById<Button>(root, R.id.favoritesBtn)?.setOnClickListener {
            val intent = Intent(activity, MyFavoritesActivity::class.java)
            activity?.startActivity(intent)
        }

        /* SETTINGS BUTTON */
        ViewBindings.findChildViewById<Button>(root, R.id.settingsBtn)?.setOnClickListener {
            val intent = Intent(activity, SettingsActivity::class.java)
            activity?.startActivity(intent)
        }

        return root
    }

    override fun onResume() {
        super.onResume()

        val retrieveUser = CoroutineScope(Dispatchers.IO).launch {
            user = profileViewModel.getUser(1)
        }

        runBlocking {
            retrieveUser.join() // wait until child coroutine completes
        }

        val welcomeMsg: TextView = binding.textWelcome
        welcomeMsg.text = user.username

        val profilePicture: CircleImageView = binding.avatar
        if (user.profile_pic != null) {
            profilePicture.setImageBitmap(user.profile_pic)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
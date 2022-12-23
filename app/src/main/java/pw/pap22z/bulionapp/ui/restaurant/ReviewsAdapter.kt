package pw.pap22z.bulionapp.ui.restaurant

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.src.Review

class ReviewsAdapter(private val context: Activity, private val reviewList: ArrayList<Review>) :
    ArrayAdapter<Review>(context, R.layout.restaurant_review_item, reviewList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.restaurant_review_item, null)

        val profilePic: ImageView = view.findViewById(R.id.profilePicture)
        val username: TextView = view.findViewById(R.id.username)
        val rating: TextView = view.findViewById(R.id.rating)
        val reviewBody: TextView = view.findViewById(R.id.reviewBody)

        if (reviewList[position].user.profilePicture != null) {
            profilePic.setImageURI(reviewList[position].user.profilePicture)
        }

        username.text = reviewList[position].user.username
        rating.text = reviewList[position].rating.toString()
        reviewBody.text = reviewList[position].reviewBody

        return view
    }

}
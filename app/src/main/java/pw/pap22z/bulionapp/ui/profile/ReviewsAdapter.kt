package pw.pap22z.bulionapp.ui.profile

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.src.Review

class ReviewsAdapter (private val context: Activity, private val reviewList: ArrayList<Review>)
    : ArrayAdapter<Review>(context, R.layout.review_list_item, reviewList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.review_list_item, null)

        val restaurantName: TextView = view.findViewById(R.id.restaurantName)
        val rating: TextView = view.findViewById(R.id.rating)
        val reviewBody: TextView = view.findViewById(R.id.reviewBody)

        restaurantName.text = reviewList[position].restaurant.name
        rating.text = (reviewList[position].rating).toString()
        reviewBody.text = reviewList[position].reviewBody

        return view
    }
}
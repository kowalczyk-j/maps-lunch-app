package pw.pap22z.bulionapp.ui.profile

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.src.Review

class RestaurantReviewsAdapter (private val context: Activity, private val reviewsList: ArrayList<Review>)
    : RecyclerView.Adapter<RestaurantReviewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView: View = LayoutInflater.from(context).inflate(R.layout.restaurant_review_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (reviewsList[position].user.profilePicture != null) {
            holder.profilePic.setImageBitmap(reviewsList[position].user.profilePicture)
        }
        holder.username.text = reviewsList[position].user.username
        holder.rating.text = reviewsList[position].rating.toString()
        holder.reviewBody.text = reviewsList[position].reviewBody
    }

    override fun getItemCount(): Int {
        return reviewsList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view!!) {

        val profilePic: ImageView = view.findViewById(R.id.profilePicture)
        val username: TextView = view.findViewById(R.id.username)
        val rating: TextView = view.findViewById(R.id.rating)
        val reviewBody: TextView = view.findViewById(R.id.reviewBody)

    }
}
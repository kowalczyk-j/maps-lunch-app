package pw.pap22z.bulionapp.ui.restaurant

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.data.entities.Review

class RestaurantReviewsAdapter (private val context: Activity)
    : RecyclerView.Adapter<RestaurantReviewsAdapter.ViewHolder>() {

    private val restaurantReviews = mutableListOf<Review>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.restaurant_review_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (restaurantReviews[position].user.profile_pic != null) {
            holder.profilePic.setImageBitmap(restaurantReviews[position].user.profile_pic)
        }
        holder.username.text = restaurantReviews[position].user.username
        holder.rating.text = restaurantReviews[position].review_rating.toString()
        holder.reviewBody.text = restaurantReviews[position].review_body
    }

    override fun getItemCount(): Int {
        return restaurantReviews.size
    }

    fun setData(newData: List<Review>) {
        restaurantReviews.clear()
        restaurantReviews.addAll(newData)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val profilePic: ImageView = view.findViewById(R.id.profilePicture)
        val username: TextView = view.findViewById(R.id.username)
        val rating: TextView = view.findViewById(R.id.rating)
        val reviewBody: TextView = view.findViewById(R.id.reviewBody)

    }
}
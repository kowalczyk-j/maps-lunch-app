package pw.pap22z.bulionapp.ui.profile

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.data.entities.Review

class ReviewsAdapter(private val context: Activity) : RecyclerView.Adapter<ReviewsAdapter.ViewHolder>() {

    private val userReviews = mutableListOf<Review>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.item_private_review, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.restaurantName.text = userReviews[position].restaurant?.name
        holder.rating.text = userReviews[position].review_rating.toString()
        holder.reviewBody.text = userReviews[position].review_body
    }

    override fun getItemCount(): Int {
        return userReviews.size
    }

    fun setData(newData: List<Review>) {
        userReviews.clear()
        userReviews.addAll(newData)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val restaurantName: TextView
        val rating: TextView
        val reviewBody: TextView

        init {
            restaurantName = view.findViewById(R.id.restaurantName)
            rating = view.findViewById(R.id.rating)
            reviewBody = view.findViewById(R.id.reviewBody)
        }

    }
}
package pw.pap22z.bulionapp.ui.profile

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pw.pap22z.bulionapp.R

class ReviewsAdapter(private val context: Activity,
                     private val reviewList: ArrayList<pw.pap22z.bulionapp.src.Review>)
    : RecyclerView.Adapter<ReviewsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView: View? = null
        itemView = LayoutInflater.from(context).inflate(R.layout.review_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.restaurantName.text = reviewList[position].restaurant.name
        holder.rating.text = reviewList[position].rating.toString()
        holder.reviewBody.text = reviewList[position].reviewBody
    }

    override fun getItemCount(): Int {
        return reviewList.size
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
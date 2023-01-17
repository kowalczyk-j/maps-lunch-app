package pw.pap22z.bulionapp.ui.profile

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.data.entities.Restaurant

class FavoritesAdapter (private val context: Activity)
    : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    private lateinit var listener: onItemClickListener
    private var favoritesList = mutableListOf<Restaurant>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView: View? = null
        itemView = LayoutInflater.from(context).inflate(R.layout.favorite_list_item, parent, false)
        return ViewHolder(itemView, this.listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.restaurantLogo.load(favoritesList[position].titleImage)
        holder.restaurantName.text = favoritesList[position].name
        holder.menu.text = favoritesList[position].num_dishes.toString() + " dania"
        holder.hours.text = favoritesList[position].hour_start.toString() + ".00-" +
                favoritesList[position].hour_end.toString() + ".00"
        holder.rating.text = "%.2f".format(favoritesList[position].rating)
        holder.typeCuisine.text = "Kuchnia " + favoritesList[position].cuisine_type
        holder.price.text = "%.2f".format(favoritesList[position].price) + "zł"
        holder.isVegan.visibility = if (favoritesList[position].is_vege) View.VISIBLE else View.GONE
        holder.favorite.visibility = if (favoritesList[position].favorite) View.VISIBLE else View.GONE
    }

    override fun getItemCount(): Int {
        return favoritesList.size
    }

    fun setData(newData: List<Restaurant>) {
        favoritesList.clear()
        favoritesList.addAll(newData)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View, listener: onItemClickListener) : RecyclerView.ViewHolder(view) {

        val restaurantName: TextView = view.findViewById(R.id.restaurantName)
        val restaurantLogo: ImageView = view.findViewById(R.id.restaurantLogo)
        val rating: TextView = view.findViewById(R.id.rating)
        val menu: TextView = view.findViewById(R.id.menu)
        val hours: TextView = view.findViewById(R.id.lunchHours)
        val typeCuisine: TextView = view.findViewById(R.id.typeCuisine)
        val price: TextView = view.findViewById(R.id.price)
        val isVegan: ImageView = view.findViewById(R.id.isVegan)
        val favorite: ImageButton = view.findViewById(R.id.isFavorite)

        init {

            view.setOnClickListener {
                listener.onItemClick(absoluteAdapterPosition)
            }

        }

    }

    interface onItemClickListener {

        fun onItemClick(position: Int)

    }

    fun setOnItemClickListener(listener: onItemClickListener){

        this.listener = listener

    }
}
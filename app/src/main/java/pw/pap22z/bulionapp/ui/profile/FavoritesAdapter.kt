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

    private lateinit var listener: OnItemClickListener
    private var favoritesList = mutableListOf<Restaurant>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.favorite_list_item, parent, false)
        return ViewHolder(itemView, this.listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.restaurantLogo.load(favoritesList[position].image_title)
        holder.restaurantName.text = favoritesList[position].name
        holder.menu.text = holder.itemView.resources.getString(
            R.string.dishes_count,
            favoritesList[position].dishes_count
        )
        holder.hours.text = holder.itemView.resources.getString(
            R.string.lunch_hours,
            favoritesList[position].hour_start,
            favoritesList[position].hour_end
        )
        holder.rating.text = if(favoritesList[position].rating == 0f) {
            holder.itemView.resources.getString(R.string.no_rating)
        } else {
            holder.itemView.resources.getString(R.string.rating, favoritesList[position].rating)
        }
        holder.typeCuisine.text = holder.itemView.resources.getString(
            R.string.cuisine_type,
            favoritesList[position].cuisine_type
        )
        holder.price.text = holder.itemView.resources.getString(
            R.string.price,
            favoritesList[position].price
        )
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

    class ViewHolder(view: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(view) {

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

    interface OnItemClickListener {

        fun onItemClick(position: Int)

    }

    fun setOnItemClickListener(listener: OnItemClickListener){

        this.listener = listener

    }
}
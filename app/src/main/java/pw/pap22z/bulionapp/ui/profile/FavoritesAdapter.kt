package pw.pap22z.bulionapp.ui.profile

import android.app.Activity
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
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
        holder.restaurantName.text = favoritesList[position].titleImage
//        holder.restaurantLogo.setImageURI(Uri.parse(favoritesList[position].logoUriStr))
//        holder.rating.text = favoritesList[position].rating.toString()
        holder.menu.text = favoritesList[position].description
//        "Od ${favoritesList[position].hourStart} do ${favoritesList[position].hourEnd}".also { holder.hours.text = it }
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
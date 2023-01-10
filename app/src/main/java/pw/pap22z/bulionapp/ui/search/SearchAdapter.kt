package pw.pap22z.bulionapp.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import pw.pap22z.bulionapp.data.entities.Restaurant
import pw.pap22z.bulionapp.databinding.FavoriteListItemBinding

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {

    private val allRestaurants = mutableListOf<Restaurant>()

    class MyViewHolder(val binding: FavoriteListItemBinding, listener: onItemClickListener) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            FavoriteListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.restaurantLogo.load(allRestaurants[position].titleImage)
        holder.binding.restaurantName.text = allRestaurants[position].name
        holder.binding.menu.text = allRestaurants[position].num_dishes.toString() + " dania"
        holder.binding.lunchHours.text = allRestaurants[position].hour_start.toString() + ".00-" +
                allRestaurants[position].hour_end.toString() + ".00"
        holder.binding.rating.text = allRestaurants[position].rating.toString()
        holder.binding.typeCuisine.text = "Kuchnia " + allRestaurants[position].cuisine_type
        holder.binding.price.text = allRestaurants[position].price.toString() + "zł"
        holder.binding.isVegan.visibility = if (allRestaurants[position].is_vege) View.VISIBLE else View.GONE
    }

    override fun getItemCount(): Int {
        return allRestaurants.size
    }

    fun setData(newData: List<Restaurant>){
        allRestaurants.clear()
        allRestaurants.addAll(newData)
        notifyDataSetChanged()
    }

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemCLickListener(listener: onItemClickListener){
        mListener = listener
    }

}
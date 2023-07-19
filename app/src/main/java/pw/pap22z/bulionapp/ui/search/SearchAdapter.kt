package pw.pap22z.bulionapp.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.data.entities.Restaurant
import pw.pap22z.bulionapp.databinding.ItemRestaurantBinding

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {

    private val allRestaurants = mutableListOf<Restaurant>()
    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class MyViewHolder(val binding: ItemRestaurantBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: OnItemClickListener, position: Int) {
            itemView.setOnClickListener {
                listener.onItemClick(position)
            }
        }
    }

    fun setData(newData: List<Restaurant>){
        allRestaurants.clear()
        allRestaurants.addAll(newData)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemRestaurantBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mListener, position)
        holder.binding.restaurantLogo.load(allRestaurants[position].image_title)
        holder.binding.restaurantName.text = allRestaurants[position].name
        holder.binding.menu.text = holder.itemView.resources.getString(
            R.string.dishes_count,
            allRestaurants[position].dishes_count
        )
        holder.binding.lunchHours.text = holder.itemView.resources.getString(
            R.string.lunch_hours,
            allRestaurants[position].hour_start,
            allRestaurants[position].hour_end
        )
        holder.binding.rating.text = if(allRestaurants[position].rating == 0f) {
            holder.itemView.resources.getString(R.string.no_rating)
        } else {
            holder.itemView.resources.getString(R.string.rating, allRestaurants[position].rating)
        }
        holder.binding.typeCuisine.text = holder.itemView.resources.getString(
            R.string.cuisine_type,
            allRestaurants[position].cuisine_type
        )
        holder.binding.price.text = holder.itemView.resources.getString(
            R.string.price,
            allRestaurants[position].price
        )
        holder.binding.isVegan.visibility = if (allRestaurants[position].is_vege) View.VISIBLE else View.GONE
        holder.binding.isFavorite.visibility = if (allRestaurants[position].favorite) View.VISIBLE else View.GONE
    }

    override fun getItemCount(): Int {
        return allRestaurants.size
    }

    fun setOnItemCLickListener(listener: OnItemClickListener){
        mListener = listener
    }

}
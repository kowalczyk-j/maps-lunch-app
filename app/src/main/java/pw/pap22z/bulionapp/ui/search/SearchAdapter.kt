package pw.pap22z.bulionapp.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.src.RestaurantSearch

class SearchAdapter(private var restaurantsList: ArrayList<RestaurantSearch>) : RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val titleImage: ImageView = itemView.findViewById(R.id.title_image)
        val desciption : TextView = itemView.findViewById(R.id.tvDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.search_list_item,
        parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = restaurantsList[position]
        holder.titleImage.setImageResource(currentItem.titleImage)
        holder.desciption.text = currentItem.description
    }

    override fun getItemCount(): Int {
        return restaurantsList.size
    }

    fun filterList(filterlist: ArrayList<RestaurantSearch>){
        restaurantsList = filterlist
        notifyDataSetChanged()
    }

}
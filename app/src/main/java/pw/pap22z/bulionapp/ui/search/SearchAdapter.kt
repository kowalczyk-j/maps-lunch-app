package pw.pap22z.bulionapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pw.pap22z.bulionapp.data.Restaurant
import pw.pap22z.bulionapp.databinding.SearchListItemBinding

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {

    private val allRestaurants = ArrayList<Restaurant>()

    class MyViewHolder(val binding: SearchListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            SearchListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.titleImageTV.text = allRestaurants[position].titleImage
        holder.binding.descriptionTV.text = allRestaurants[position].description
    }

    override fun getItemCount(): Int {
        return allRestaurants.size
    }

    fun setData(newData: List<Restaurant>){
        allRestaurants.clear()
        allRestaurants.addAll(newData)
        notifyDataSetChanged()
    }

}
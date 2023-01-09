package pw.pap22z.bulionapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pw.pap22z.bulionapp.data.entities.Restaurant
import pw.pap22z.bulionapp.databinding.SearchListItemBinding

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {

    private val allRestaurants = mutableListOf<Restaurant>()

    class MyViewHolder(val binding: SearchListItemBinding, listener: onItemClickListener) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            SearchListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), mListener)
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

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemCLickListener(listener: onItemClickListener){
        mListener = listener
    }

}
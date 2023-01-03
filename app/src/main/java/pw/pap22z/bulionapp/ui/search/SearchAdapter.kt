package pw.pap22z.bulionapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pw.pap22z.bulionapp.data.Restaurant
import pw.pap22z.bulionapp.databinding.SearchListItemBinding

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {

    private var oldData = emptyList<Restaurant>()

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
        holder.binding.titleImageTV.text = oldData[position].titleImage
        holder.binding.descriptionTV.text = oldData[position].description
    }

    override fun getItemCount(): Int {
        return oldData.size
    }

    fun setData(newData: List<Restaurant>){
        oldData = newData
        notifyDataSetChanged()
    }

}
package pw.pap22z.bulionapp.ui.profile

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.src.Restaurant

class FavoritesAdapter (private val context: Activity, private val favoritesList: ArrayList<Restaurant>)
    : ArrayAdapter<Restaurant>(context, R.layout.favorite_list_item, favoritesList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.favorite_list_item, null)

        val restaurantName: TextView = view.findViewById(R.id.restaurantName)
        val restaurantLogo: ImageView = view.findViewById(R.id.restaurantLogo)
        val rating: TextView = view.findViewById(R.id.rating)
        val menu: TextView = view.findViewById(R.id.menu)
        val hours: TextView = view.findViewById(R.id.lunchHours)

        restaurantName.text = favoritesList[position].name
        rating.text = (favoritesList[position].rating).toString()
        restaurantLogo.setImageDrawable(favoritesList[position].logo)
        menu.text = favoritesList[position].menu
        "W godzinach ${favoritesList[position].hourStart}-${favoritesList[position].hourEnd}".also { hours.text = it }

        return view
    }
}
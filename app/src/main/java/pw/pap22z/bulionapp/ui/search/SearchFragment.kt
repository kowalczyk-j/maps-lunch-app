package pw.pap22z.bulionapp.ui.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.databinding.FragmentSearchBinding
import pw.pap22z.bulionapp.src.RestaurantSearch
import java.util.*

class SearchFragment : Fragment(), MenuProvider {

    private lateinit var recyclerView: RecyclerView
    private lateinit var restaurantsArrayList: ArrayList<RestaurantSearch>
    private lateinit var tempArrayList: ArrayList<RestaurantSearch>
    private lateinit var imageId: ArrayList<Int>
    private lateinit var description: Array<String>

    private  lateinit var adapter: SearchAdapter

    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ViewModelProvider(this)[SearchViewModel::class.java]
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.search_menu_item, menu)
//        val item = menu.findItem(R.id.search_action)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {

                tempArrayList.clear()
                val searchText = newText!!.lowercase(Locale.getDefault())
                if (searchText.isNotEmpty()){

                    restaurantsArrayList.forEach {
                        if (it.description.lowercase(Locale.getDefault()).contains(searchText)){
                            tempArrayList.add(it)
                        }
                    }
                    recyclerView.adapter!!.notifyDataSetChanged()

                }else{

                    tempArrayList.clear()
                    tempArrayList.addAll(restaurantsArrayList)
                    recyclerView.adapter!!.notifyDataSetChanged()

                }
                return false
            }
        })
        return false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setContentView(R.layout.fragment_search)
        dataInitialize()
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        adapter = SearchAdapter(tempArrayList)
        recyclerView.adapter = adapter
    }

//    @Deprecated("Deprecated in Java")
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.search_menu_item,menu)
//        val item = menu.findItem(R.id.search_action)
//        val searchView = item.actionView as SearchView
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//
//                tempArrayList.clear()
//                val searchText = newText!!.lowercase(Locale.getDefault())
//                if (searchText.isNotEmpty()){
//
//                    restaurantsArrayList.forEach {
//                        if (it.description.lowercase(Locale.getDefault()).contains(searchText)){
//                            tempArrayList.add(it)
//                        }
//                    }
//                    recyclerView.adapter!!.notifyDataSetChanged()
//
//                }else{
//
//                    tempArrayList.clear()
//                    tempArrayList.addAll(restaurantsArrayList)
//                    recyclerView.adapter!!.notifyDataSetChanged()
//
//                }
//                return false
//            }
//        })
//
//        @Suppress("DEPRECATION")
//        super.onCreateOptionsMenu(menu, inflater)
//    }
    private fun dataInitialize(){
        restaurantsArrayList = arrayListOf()
        tempArrayList = arrayListOf()
        imageId = arrayListOf(
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e
        )
        description = arrayOf(
            getString(R.string.description_1),
            getString(R.string.description_2),
            getString(R.string.description_3),
            getString(R.string.description_4),
            getString(R.string.description_5),

        )
        for(i in imageId.indices){
            val restaurant = RestaurantSearch(imageId[i], description[i])
            restaurantsArrayList.add(restaurant)
        }
        tempArrayList.addAll(restaurantsArrayList)
    }
}


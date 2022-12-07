package pw.pap22z.bulionapp.ui.search

import android.os.Bundle
import android.view.*
import android.widget.Toast
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

class SearchFragment : Fragment(), MenuProvider {

    private lateinit var recyclerView: RecyclerView
    private lateinit var restaurantsArrayList: ArrayList<RestaurantSearch>
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
        val root = inflater.inflate(R.layout.fragment_search, container, false)
//        recyclerView = root.findViewById(R.id.recycler_view)
//        restaurantsArrayList = ArrayList()
//        dataInitialize()
//        adapter = SearchAdapter(restaurantsArrayList)
//        recyclerView.adapter = adapter
//        adapter.notifyDataSetChanged()
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setContentView(R.layout.fragment_search)
        recyclerView = view.findViewById(R.id.recycler_view)
        restaurantsArrayList = ArrayList()
        adapter = SearchAdapter(restaurantsArrayList)
        recyclerView.adapter = adapter
        dataInitialize()
        adapter.notifyDataSetChanged()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.search_menu_item, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                filter(newText)
                return false
            }
        })
        return true
    }

    private fun filter(text: String){
        val filteredlist: ArrayList<RestaurantSearch> = ArrayList()
        for (item in restaurantsArrayList){
            if (item.description.lowercase().contains(text.lowercase())){
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()){
            Toast.makeText(activity, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            adapter.filterList((filteredlist))
        }
    }


    private fun dataInitialize(){
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
    }
}


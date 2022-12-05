package pw.pap22z.bulionapp.ui.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.databinding.FragmentSearchBinding
import pw.pap22z.bulionapp.src.RestaurantSearch
import java.util.*

class SearchFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var newsArrayList: ArrayList<RestaurantSearch>
    private lateinit var tempArrayList: ArrayList<RestaurantSearch>
    lateinit var imageId: ArrayList<Int>
    lateinit var description: Array<String>

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
        val searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setContentView(R.layout.fragment_search)
        //błądsetHasOptionsMenu(true)
        dataInitialize()
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        adapter = SearchAdapter(tempArrayList)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu_item,menu)
        val item = menu.findItem(R.id.search_action)
        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                tempArrayList.clear()
                val searchText = newText!!.lowercase(Locale.getDefault())
                if (searchText.isNotEmpty()){

                    newsArrayList.forEach {
                        if (it.description.lowercase(Locale.getDefault()).contains(searchText)){
                            tempArrayList.add(it)
                        }
                    }
                    recyclerView.adapter!!.notifyDataSetChanged()

                }else{

                    tempArrayList.clear()
                    tempArrayList.addAll(newsArrayList)
                    recyclerView.adapter!!.notifyDataSetChanged()

                }
                return false
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }
    private fun dataInitialize(){
        newsArrayList = arrayListOf<RestaurantSearch>()
        tempArrayList = arrayListOf<RestaurantSearch>()
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
//        news = arrayOf(
//            getString(R.string.news_a),
//            getString(R.string.news_b),
//            getString(R.string.news_c),
//            getString(R.string.news_d),
//            getString(R.string.news_e),
//            getString(R.string.news_f),
//            getString(R.string.news_g),
//            getString(R.string.news_h),
//            getString(R.string.news_i),
//            getString(R.string.news_j),
//        )
        for(i in imageId.indices){
            val restaurant = RestaurantSearch(imageId[i], description[i])
            newsArrayList.add(restaurant)
        }
        tempArrayList.addAll(newsArrayList)
    }
}


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
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.databinding.FragmentSearchBinding

//@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search), MenuProvider {

    // This property is only valid between onCreateView and
    // onDestroyView.
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    //private val searchViewModel: SearchViewModel by viewModels()
    lateinit var viewModel: SearchViewModel
    //private val sAdapter: SearchAdapter by lazy { SearchAdapter() }

    //private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //val searchViewModel =
            //ViewModelProvider(this).get(SearchViewModel::class.java)
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        //val root = inflater.inflate(R.layout.fragment_search, container, false)
//        val menuHost: MenuHost = requireActivity()
//        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        //binding.recyclerView.layoutManager = LinearLayoutManager(context)
        //binding.recyclerView.adapter = sAdapter
//        recyclerView.setHasFixedSize(true)
        //searchViewModel.insertData(Restaurant("Aioli", "Włoska"))
//        searchViewModel.readData.observe(viewLifecycleOwner) {
//            sAdapter.setData(it)
//        }
        return binding.root
    }

    //    _binding = FragmentSearchBinding.inflate(inflater, container, false)
//        binding.recyclerView.layoutManager = LinearLayoutManager(context)
//        binding.recyclerView.adapter = SearchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSearchBinding.bind(view)
        val sAdapter = SearchAdapter()
        binding.apply {
            recyclerView.apply {
                adapter = sAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(SearchViewModel::class.java)
        //searchViewModel.insertData(Restaurant("Aioli", "Włoska"))
        //sAdapter.setData(listOf(Restaurant("aioli", "test1"), Restaurant("lapose", "test2")))
//        searchViewModel.readData.observe(viewLifecycleOwner) {
//            sAdapter.setData(it)
//        }
        //search
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
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
                return true
            }
            override fun onQueryTextChange(query: String): Boolean {
//                if(query != null){
//                    val searchQuery = "%$query%"
//
//                    searchViewModel.searchDatabase(searchQuery).observe(viewLifecycleOwner) { list ->
//                        list.let {
//                            adapter.setData(it)
//                        }
//                    }
//                }
                return true
            }
        })
        return true
    }

//    private fun filter(text: String){
//        val filteredlist: ArrayList<RestaurantSearch> = ArrayList()
//        for (item in restaurantsArrayList){
//            if (item.description.lowercase().contains(text.lowercase())){
//                filteredlist.add(item)
//            }
//        }
//        if (filteredlist.isEmpty()){
//            Toast.makeText(activity, "No Data Found..", Toast.LENGTH_SHORT).show()
//        } else {
//            adapter.filterList((filteredlist))
//        }
//    }
//
//
//    private fun dataInitialize(){
//        imageId = arrayListOf(
//            R.drawable.a,
//            R.drawable.b,
//            R.drawable.c,
//            R.drawable.d,
//            R.drawable.e
//        )
//        description = arrayOf(
//            getString(R.string.description_1),
//            getString(R.string.description_2),
//            getString(R.string.description_3),
//            getString(R.string.description_4),
//            getString(R.string.description_5),
//
//        )
//        for(i in imageId.indices){
//            val restaurant = RestaurantSearch(imageId[i], description[i])
//            restaurantsArrayList.add(restaurant)
//        }
//    }
}


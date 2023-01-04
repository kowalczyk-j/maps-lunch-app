package pw.pap22z.bulionapp.ui.search

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.databinding.FragmentSearchBinding

class SearchFragment : Fragment(R.layout.fragment_search), MenuProvider {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    //private val searchViewModel: SearchViewModel by viewModels()
    lateinit var viewModel: SearchViewModel
    private val sAdapter: SearchAdapter by lazy { SearchAdapter() }
    //private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
//        searchViewModel.readData.observe(viewLifecycleOwner) {
//            sAdapter.setData(it)
//        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSearchBinding.bind(view)
        //val sAdapter = SearchAdapter()
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
//        viewModel.insertData(Restaurant("si ristorante", "test2"))
//        viewModel.insertData(Restaurant("aioli", "test1"))
        //sAdapter.setData(listOf(Restaurant("aioli", "test1"), Restaurant("lapose", "test2")))
        viewModel.allRestaurants.observe(viewLifecycleOwner) { list ->
            list?.let {
                sAdapter.setData(it)
            }
        }
        //search
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.search_menu_item, menu)
    }

    override fun onPrepareMenu(menu: Menu) {
        Toast.makeText(requireContext(), "prepared", Toast.LENGTH_SHORT).show()
        super.onPrepareMenu(menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId) {
            R.id.search_action -> {
                Toast.makeText(requireContext(), "item selected", Toast.LENGTH_SHORT).show()
                return false
            }
            }
        //val searchView = menuItem.actionView as SearchView
        //Toast.makeText(requireContext(), "item selected", Toast.LENGTH_SHORT).show()
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String): Boolean {
//                Toast.makeText(requireContext(), "submit", Toast.LENGTH_SHORT).show()
//                return false
//            }
//            override fun onQueryTextChange(query: String): Boolean {
//                Toast.makeText(requireContext(), "text changed", Toast.LENGTH_SHORT).show()
////                if(query.isNotEmpty()){
////                    val searchQuery = "%$query%"
////
////                    viewModel.searchDatabase(searchQuery).observe(viewLifecycleOwner) { list ->
////                        list.let {
////                            sAdapter.setData(it)
////                        }
////                    }
////                }
//                return false
//            }
//        })
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


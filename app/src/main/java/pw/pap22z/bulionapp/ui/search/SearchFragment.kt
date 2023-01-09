package pw.pap22z.bulionapp.ui.search

import android.content.Intent
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
import pw.pap22z.bulionapp.data.entities.Lunch
import pw.pap22z.bulionapp.data.entities.Restaurant
import pw.pap22z.bulionapp.databinding.FragmentSearchBinding
import pw.pap22z.bulionapp.ui.restaurant.RestaurantActivity

class SearchFragment : Fragment(R.layout.fragment_search), MenuProvider {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: SearchViewModel
    private val sAdapter: SearchAdapter by lazy { SearchAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSearchBinding.bind(view)
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

        viewModel.insertRestaurant(Restaurant(1,"si ristorante", "test id 1"))
        viewModel.insertRestaurant(Restaurant(2,"aioli", "test id 2"))
        viewModel.insertRestaurant(Restaurant(3,"Lapose", "test id 3"))
        viewModel.insertLunch(Lunch(1, "lunch do si ristorante", 1))
        viewModel.insertLunch(Lunch(2, "lunch do aioli", 2))
        viewModel.insertLunch(Lunch(3, "lunch do lapose", 3))

        viewModel.allRestaurants.observe(viewLifecycleOwner) { list ->
            list?.let {
                sAdapter.setData(it)
            }
        }


        sAdapter.setOnItemCLickListener(object : SearchAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val restaurant = viewModel.allRestaurants.value?.get(position)
                val res_id = restaurant?.restaurant_id
                viewModel.getLunchWithRestaurant(res_id!!)
//                Toast.makeText(requireContext(), "Position: $position . lunch: $lunch restaurant id: $res_id", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, RestaurantActivity::class.java)
                intent.putExtra("restaurant", restaurant)
                startActivity(intent)
            }
        })
        //search
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.search_menu_item, menu)
        val itemView = menu.findItem(R.id.search_action)
        val searchView = itemView.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }
            override fun onQueryTextChange(query: String): Boolean {
                val searchQuery = "%$query%"
                viewModel.searchRestaurant(searchQuery).observe(viewLifecycleOwner) { list ->
                    list.let {
                        sAdapter.setData(it)
                    }
                }
                return true
            }
        })
    }


    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


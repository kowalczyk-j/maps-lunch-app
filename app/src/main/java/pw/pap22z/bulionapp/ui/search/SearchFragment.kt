package pw.pap22z.bulionapp.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.data.entities.Restaurant
import pw.pap22z.bulionapp.databinding.FragmentSearchBinding
import pw.pap22z.bulionapp.ui.restaurant.RestaurantActivity

class SearchFragment : Fragment(R.layout.fragment_search), MenuProvider {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    lateinit var searchViewModel: SearchViewModel
    private val sAdapter: SearchAdapter by lazy { SearchAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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

        searchViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[SearchViewModel::class.java]

        searchViewModel.allRestaurants.observe(viewLifecycleOwner) { list ->
            list?.let { sAdapter.setData(it) }
        }

        sAdapter.setOnItemCLickListener(object : SearchAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val restaurant = searchViewModel.allRestaurants.value?.get(position)
                val intent = Intent(context, RestaurantActivity::class.java)
                intent.putExtra("restaurant", restaurant)
                startActivity(intent)
            }
        })

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.search_sort_menu, menu)
        val itemView = menu.findItem(R.id.search_action)
        val searchView = itemView.actionView as SearchView
        searchView.queryHint = R.string.search_hint.toString()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                val searchQuery = "%$query%"
                searchViewModel.searchRestaurant(searchQuery).observe(viewLifecycleOwner) { list ->
                    list.let { sAdapter.setData(it) }
                }
                return true
            }
        })
    }


    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_sort_by_name -> {
                sortBy(searchViewModel.getRestaurants(), R.string.sorted_alphabetically)
                true
            }

            R.id.action_sort_by_vegan -> {
                sortBy(searchViewModel.sortRestaurantsByVegan(), R.string.show_vege_options)
                true
            }

            R.id.action_sort_by_dishes -> {
                sortBy(searchViewModel.sortRestaurantsByDishes(), R.string.sorted_by_dishes)
                true
            }

            R.id.action_sort_by_price -> {
                sortBy(searchViewModel.sortRestaurantsByPrice(), R.string.sorted_by_price)
                true
            }

            R.id.action_sort_by_price_reverse -> {
                sortBy(searchViewModel.sortRestaurantsByPriceDESC(), R.string.sorted_by_price_reverse)
                true
            }

            R.id.action_sort_by_rating -> {
                sortBy(searchViewModel.sortRestaurantsByRating(), R.string.sorted_by_rating)
                true
            }

            R.id.action_sort_by_hours -> {
                sortBy(searchViewModel.sortRestaurantsByCloseHour(), R.string.sorted_by_hours)
                true
            }

            else -> true
        }
    }

    private fun sortBy(sortData: LiveData<List<Restaurant>>, toastMessage: Int) {
        sortData.observe(viewLifecycleOwner) { list ->
            list.let { sAdapter.setData(it) }
        }
        Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    private fun refreshData() {
        searchViewModel.getRestaurants().observe(viewLifecycleOwner) { list ->
            list.let { sAdapter.setData(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


package pw.pap22z.bulionapp.ui.search

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.launch
import pw.pap22z.bulionapp.R
import pw.pap22z.bulionapp.databinding.FragmentSearchBinding
import pw.pap22z.bulionapp.ui.restaurant.RestaurantActivity
import pw.pap22z.bulionapp.data.entities.Restaurant

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

        lifecycleScope.launch{
            viewModel.insertRestaurant(Restaurant(1, getBitmap("https://sztuczne-rosliny.pl/wp-content/uploads/2020/01/aioli-logo.jpg"),
                "Aioli", 4.5F, "Włoska", "Chmielna 26", 26.90F, 12, 17, 3, true,
            "Zupa: zupa z zielonym groszkiem\n 1 z 3 dań głównych do wyboru: \n1. Chutney Jalapeno Burger \n2. Tuna Salad \n3. Pizza Chorizo\n Minideser: Budyń "))
            viewModel.insertRestaurant(Restaurant(2,getBitmap("https://i.postimg.cc/c1fxvrvS/274794981-3190194264597526-5426536868885463001-n.jpg"),
                "Si Ristorante", 4.7F, "Włoska", "Marszałkowska 115", 27.90F, 12, 16, 3, false,
                "Zupa: Zupa soczewicowa z pomidorami i suszoną miętą\n 1 z 2 dań głównych do wyboru: 1. Pierś indyka z krokietem z dynią, duszonymi pieczarkami i sosem ziołowym" +
                        " 2. Calzone ze szpinakiem i camembertem\n Deser: Brownie czekoladowe "))
            viewModel.insertRestaurant(Restaurant(3,getBitmap("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTc8rJzveezG9V3n6LJ-URWPi6znY4PIaVk2g&usqp=CAU"),
                "La Pose", 4.2F, "Amerykańska, włoska, fusion", "Mazowiecka 6/8", 24.90F, 12, 17, 2, true,
                "Pon.-pt.:\nZupa dnia + 1 z 12 dań głównych do wyboru\nOferta specjalna: Do kawy/herbaty wybrane śniadanie za 1 zł codziennie do 13.00"))
        }

        viewModel.allRestaurants.observe(viewLifecycleOwner) { list ->
            list?.let { sAdapter.setData(it) }
        }

        sAdapter.setOnItemCLickListener(object : SearchAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val restaurant = viewModel.allRestaurants.value?.get(position)
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
        searchView.queryHint = "Znajdź swój ulubiony bulion..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }
            override fun onQueryTextChange(query: String): Boolean {
                val searchQuery = "%$query%"
                viewModel.searchRestaurant(searchQuery).observe(viewLifecycleOwner) { list ->
                    list.let { sAdapter.setData(it) }
                }
                return true
            }
        })
    }


    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_sort_by_name -> {
                viewModel.getRestaurants().observe(viewLifecycleOwner) { list ->
                    list.let { sAdapter.setData(it) }
                }
                Toast.makeText(requireContext(), "Posortowano alfabetycznie", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_sort_by_vegan -> {
                viewModel.sortRestaurantsByVege().observe(viewLifecycleOwner) { list ->
                    list.let { sAdapter.setData(it) }
                }
                Toast.makeText(requireContext(), "Pokazano tylko restauracje z opcjami vege", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_sort_by_dishes -> {
                viewModel.sortRestaurantsByDishes().observe(viewLifecycleOwner) { list ->
                    list.let { sAdapter.setData(it) }
                }
                Toast.makeText(requireContext(), "Posortowano od największej liczby dań", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_sort_by_price -> {
                viewModel.sortRestaurantsByPrice().observe(viewLifecycleOwner) { list ->
                    list.let { sAdapter.setData(it) }
                }
                Toast.makeText(requireContext(), "Posortowano od najtańszych", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_sort_by_price_reverse -> {
                viewModel.sortRestaurantsByPriceDESC().observe(viewLifecycleOwner) { list ->
                    list.let { sAdapter.setData(it) }
                }
                Toast.makeText(requireContext(), "Posortowano od najdroższych", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_sort_by_rating -> {
                viewModel.sortRestaurantsByRating().observe(viewLifecycleOwner) { list ->
                    list.let { sAdapter.setData(it) }
                }
                Toast.makeText(requireContext(), "Posortowano od najlepiej ocenianych", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_sort_by_hours -> {
                viewModel.sortRestaurantsByCloseHour().observe(viewLifecycleOwner) { list ->
                    list.let { sAdapter.setData(it) }
                }
                Toast.makeText(requireContext(), "Posortowano od najdłużej dostępnych", Toast.LENGTH_SHORT).show()
                true
            }
            else -> true
    }}

    private  suspend fun getBitmap(url: String): Bitmap {
        val loading = ImageLoader(context!!)
        val request = ImageRequest.Builder(context!!)
            .data(url)
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    fun refreshData() {
        viewModel.getRestaurants().observe(viewLifecycleOwner) { list ->
            list.let { sAdapter.setData(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


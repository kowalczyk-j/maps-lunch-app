package pw.pap22z.bulionapp

import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pw.pap22z.bulionapp.data.RestaurantDatabase
import pw.pap22z.bulionapp.data.entities.Restaurant
import pw.pap22z.bulionapp.data.entities.User
import pw.pap22z.bulionapp.databinding.ActivityMainBinding
import pw.pap22z.bulionapp.ui.restaurant.RestaurantViewModel

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var restaurantViewModel: RestaurantViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_map, R.id.navigation_search, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        restaurantViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
        )[RestaurantViewModel::class.java]

        RestaurantDatabase.getDatabase(this)

        initUser()
        initData()
    }

    private fun initData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val bitmapAioli =
                async { getBitmap("https://i.postimg.cc/NMycjLZC/aioli-logo.jpg") }.await()
            val bitmapSiRistorante =
                async { getBitmap("https://i.postimg.cc/Y9007rpb/si-ristorante.jpg") }.await()
            val bitmapLapose =
                async { getBitmap("https://i.postimg.cc/JnF26q6P/lapose-logo.jpg") }.await()
            val bitmapSiDue =
                async { getBitmap("https://i.postimg.cc/RVw7nMXW/318562116-122736723984972-1435831359345702240-n.jpg") }.await()
            val bitmapZnajomi =
                async { getBitmap("https://i.postimg.cc/RFf7Q44p/290202066-5329847933741259-3044643926349662777-n.jpg") }.await()
            val bitmapBordoBistro =
                async { getBitmap("https://i.postimg.cc/pTGHCbC8/307892682-617981183069691-3572231132101971245-n.jpg") }.await()

            withContext(Dispatchers.Main) {
                restaurantViewModel.insertRestaurant(
                    Restaurant(
                        1,
                        bitmapAioli,
                        "Aioli",
                        0.0f,
                        "Włoska",
                        "Chmielna 26, Warszawa",
                        26.90F,
                        12,
                        17,
                        3,
                        true,
                        "Zupa: zupa z zielonym groszkiem\n 1 z 3 dań głównych do wyboru: \n1. Chutney Jalapeno Burger \n2. Tuna Salad \n3. Pizza Chorizo\n Minideser: Budyń ",
                        menu = "https://www.facebook.com/AIOLICantineSwietokrzyska/photos/a.540039119386448/6038734066183565/"
                    )
                )

                restaurantViewModel.insertRestaurant(
                    Restaurant(
                        2,
                        bitmapSiRistorante,
                        "Si Ristorante",
                        0.0f,
                        "Włoska",
                        "Marszałkowska 115",
                        27.90F,
                        12,
                        16,
                        3,
                        false,
                        "Zupa: Zupa soczewicowa z pomidorami i suszoną miętą\n 1 z 2 dań głównych do wyboru: 1. Pierś indyka z krokietem z dynią, duszonymi pieczarkami i sosem ziołowym" + " 2. Calzone ze szpinakiem i camembertem\n Deser: Brownie czekoladowe ",
                        menu = "https://www.facebook.com/RestauracjaSi/posts/pfbid02NXgRnzB7yruRAYDkSbePgTreu76z1EjXNJkR83Vo2Qh6HE3ifcgUHaDUL82CPGNZl"
                    )
                )

                restaurantViewModel.insertRestaurant(
                    Restaurant(
                        3,
                        bitmapLapose,
                        "La Pose",
                        0.0f,
                        "Amerykańska, włoska, fusion",
                        "Mazowiecka 6/8",
                        24.90F,
                        12,
                        17,
                        2,
                        true,
                        "Pon.-pt.:\nZupa dnia + 1 z 12 dań głównych do wyboru\nOferta specjalna: Do kawy/herbaty wybrane śniadanie za 1 zł codziennie do 13.00",
                        menu = "http://lapose.pl/wp-content/uploads/2022/12/LaPoseVarsovie_menugrudzien.pdf"
                    )
                )

                restaurantViewModel.insertRestaurant(
                    Restaurant(
                        4,
                        bitmapSiDue,
                        "Si Due",
                        0.0f,
                        "Włoska",
                        "Marszałkowska 62",
                        27.90F,
                        12,
                        16,
                        3,
                        true,
                        "Zupa Minestrone z pomidorami i Grana Padano\n" + "Pulpety wieprzowe z puree ziemniaczanym, glazurowaną marchewką i sosem grzybowym lub \n" + "Fettuccine Putanesca\n" + "Tarta czekoladowa"
                    )
                )

                restaurantViewModel.insertRestaurant(
                    Restaurant(
                        5,
                        bitmapZnajomi,
                        "Znajomi",
                        0.0f,
                        "Europejska",
                        "Wilcza 58a",
                        22.0F,
                        12,
                        16,
                        2,
                        true,
                        "Ogórkowa z ziemniakami i koperkiem\n" + "Tarta z ciasta francuskiego ze szpinakiem i serem lazur lub Ragout z indyka w kremowo pomdorowym sosie z ryżem",
                        menu = "https://www.facebook.com/znajomiznajomychFB/posts/pfbid0Sq1SCu8kt5Db5XMdLzTXGQtpTqCRmiFDduSsaqgoJhFegVpDYVL4JmfQBz4efwhxl"
                    )
                )

                restaurantViewModel.insertRestaurant(
                    Restaurant(
                        6,
                        bitmapBordoBistro,
                        "Bordo Bistro",
                        0.0f,
                        "Europejska",
                        "Chmielna 34, Warszawa",
                        23.0F,
                        12,
                        16,
                        3,
                        true,
                        "Zupa z soczewicy\nGnocchi z pesto bazyliowym i pomidorkami lub Polędwiczki wieprzowe w sosie musztardowym z puree i surówką lub " + "Pizza z szynką i pieczarkami\nMini bezy z musem owocowym",
                        menu = "https://www.facebook.com/BistroChmielna/posts/pfbid0bj2VPjtyGX3wWuYb6r15mQzFiWg4vfhrSn5P2tRmWy9qpMaArRRVeuiTAdcC8PWPl"
                    )
                )

            }
        }
    }

    private fun initUser() {
        lifecycleScope.launch(Dispatchers.IO) {
            val defaultUser = User(1, "Kinga", null)
            restaurantViewModel.insertUser(defaultUser)
        }
    }

    private fun getBitmap(url: String): Bitmap {
        return Glide.with(this).asBitmap().load(url).submit().get()
    }
}


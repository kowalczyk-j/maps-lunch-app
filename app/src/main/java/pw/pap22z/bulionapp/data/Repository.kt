package pw.pap22z.bulionapp.data


//class Repository(private val restaurantDao: RestaurantDao, private val lunchDao: LunchDao) {
//
//    //val allRestaurants: LiveData<List<Restaurant>> = getRestaurants()
//
//    fun getRestaurants(): LiveData<List<Restaurant>> {
//        return restaurantDao.getRestaurants()
//    }
//
//    fun getLunchWithRestaurant(restaurantId: Int): LiveData<Lunch> {
//        return lunchDao.getLunchWithRestaurant(restaurantId)
//    }
//
//    fun getLunches() : LiveData<List<Lunch>> {
//        return lunchDao.getLunches()
//    }
//
//    suspend fun insertRestaurant(restaurant: Restaurant) {
//        restaurantDao.insertRestaurant(restaurant)
//    }
//
//    suspend fun insertLunch(lunch: Lunch) {
//        lunchDao.insertLunch(lunch)
//    }
//
//    fun searchRestaurant(searchQuery: String): LiveData<List<Restaurant>> {
//        return restaurantDao.searchRestaurant(searchQuery)
//    }
//
//}
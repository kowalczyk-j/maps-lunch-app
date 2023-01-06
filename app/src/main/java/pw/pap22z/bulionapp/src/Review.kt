package pw.pap22z.bulionapp.src

class Review constructor(var rating: Double, var reviewBody: String, var restaurant: Restaurant,
                         var user: User) {

    init {
        restaurant.reviews.add(this)
        user.reviews.add(this)
    }

}
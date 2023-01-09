package pw.pap22z.bulionapp.src

<<<<<<< app/src/main/java/pw/pap22z/bulionapp/src/Review.kt
class Review constructor(var rating: Double, var reviewBody: String,
                         var user: User) {
}
=======
import java.io.Serializable

data class Review (var rating: Double, var reviewBody: String, var restaurant: Restaurant,
                   var user: User) : Serializable
>>>>>>> app/src/main/java/pw/pap22z/bulionapp/src/Review.kt
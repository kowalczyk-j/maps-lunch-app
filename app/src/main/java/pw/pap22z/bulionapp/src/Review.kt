package pw.pap22z.bulionapp.src
import java.io.Serializable

data class Review (var rating: Double, var reviewBody: String, var restaurant: Restaurant,
                   var user: User) : Serializable


package pw.pap22z.bulionapp.src

import java.io.Serializable


data class Restaurant (val name: String, val address: String, val logoUriStr: String,
                       val menu: String, val hourStart: String, val hourEnd: String,
                       var reviews: ArrayList<Review> = arrayListOf(), var rating: Double = 0.0) :
    Serializable
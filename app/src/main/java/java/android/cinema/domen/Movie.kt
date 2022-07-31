package java.android.cinema.domen

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.android.cinema.utils.UserRandom

@Parcelize
class Movie(val title: String):Parcelable{
    private var duration:String? = null
    private var rating:String? = null
    var userRating:Double = 5.0
    var adultBorder = UserRandom.trueFalse()
    var urlImage:String = "default"

    fun setDescription(rating:Int,duration:Int){
        this.rating =  "$rating stars"
        this.duration = "$duration minutes"
    }

    fun changeRating(rating: Double){
        this.userRating = rating
    }

    fun setImage(urlImage:String){
        this.urlImage = urlImage
    }

    fun getDescription():String{
        val textRating:String = rating?:"Нет данных"
        val textDuration:String = duration?:"Нет данных"

        return "$textRating $textDuration"
    }

}

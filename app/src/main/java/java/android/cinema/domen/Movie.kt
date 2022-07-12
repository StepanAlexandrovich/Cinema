package java.android.cinema.domen

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Movie(val title: String):Parcelable{
    private var duration:String? = null
    private var rating:String? = null

    // временно
        // то что имеем
    val lat = 2
    val lon = 3
        // то что получим из интернета
    var temperature = 0
    var feelsLike = 0

    fun setDescription(rating:Int,duration:Int){
        this.rating =  "$rating stars"
        this.duration = "$duration minutes"
    }

    fun getDescription():String{
        val textRating:String = rating?:"Нет данных"
        val textDuration:String = duration?:"Нет данных"

        //return "$textRating $textDuration" // расскоментирую

        return "$temperature $feelsLike"   // временно
    }

}

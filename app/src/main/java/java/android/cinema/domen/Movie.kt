package java.android.cinema.domen

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.android.cinema.utils.RandomCin

@Parcelize
class Movie(val title: String):Parcelable{
    private var duration:String? = null
    private var rating1:String? = null
    var rating:Double = 5.0
    var adultBorder = RandomCin.nextBoolean()
    var urlImage:String? = null

    fun setDescription(rating:Int,duration:Int){
        this.rating1 =  "$rating stars"
        this.duration = "$duration minutes"
    }

    fun changeRating(rating: Double){
        this.rating = rating
    }

    fun setImage(urlImage:String){
        this.urlImage = urlImage
    }

    fun getDescription():String{
        val textRating:String = rating1?:"Нет данных"
        val textDuration:String = duration?:"Нет данных"

        return "$textRating $textDuration"
    }

}

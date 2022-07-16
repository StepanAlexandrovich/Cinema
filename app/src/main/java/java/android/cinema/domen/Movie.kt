package java.android.cinema.domen

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.android.cinema.utils.RandomCin

@Parcelize
class Movie(val title: String):Parcelable{
    private var duration:String? = null
    private var rating:String? = null
    var adultBorder = RandomCin.nextBoolean()
    var urlImage:String? = null

    fun setDescription(rating:Int,duration:Int){
        this.rating =  "$rating stars"
        this.duration = "$duration minutes"
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

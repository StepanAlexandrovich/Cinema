package java.android.cinema.storage

class Movie(val title: String) {
    private var duration:String? = null
    private var rating:String? = null

    fun setDescription(rating:Int,duration:Int){
        this.rating =  "$rating stars"
        this.duration = "$duration minutes"
    }

    fun getDescription():String{
        val textRating:String = rating?:"Нет данных"
        val textDuration:String = duration?:"Нет данных"

        return "$textRating $textDuration"
    }

}
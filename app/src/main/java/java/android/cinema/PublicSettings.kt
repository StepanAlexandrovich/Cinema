package java.android.cinema

object PublicSettings {
    val stringsGenre = mutableListOf<String>("inception 2010","fringe")
    var isAdult = true

    fun switchAdult(){ isAdult = !isAdult }

    var mode:Mode? = null

    //val modeDataBase = Mode(listOf("comedy", "fantasy")) // раскоментирую

    val modeDataBase = Mode(listOf("room"))
    val modeOkHttp = Mode(listOf("inception 2010","fringe"))
    val modeRetrofit = Mode(listOf("The Leftovers 2014","lost 2004","inception 2010","fringe"))
    class Mode(val strings:List<String>){}

}
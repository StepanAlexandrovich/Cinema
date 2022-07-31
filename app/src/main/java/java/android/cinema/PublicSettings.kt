package java.android.cinema

object PublicSettings {
    const val maxNumberOfGenres = 6

    class Mode(val strings:List<String>){}

    val modeDataBase = Mode(listOf("comedy","fantasy","animation"))
    val modeTest = Mode(listOf("comedy","fantasy","animation"))
    val modeInternetStandard = Mode(listOf("inception 2010","lost 2004"))
    val modeOkHttp = Mode(listOf("inception 2010","fringe"))
    val modeRetrofit = Mode(listOf("The Leftovers 2014","lost 2004","inception 2010","fringe"))

    var mode:Mode = modeTest


    var isAdult = true
    fun switchAdult(){ isAdult = !isAdult }
}
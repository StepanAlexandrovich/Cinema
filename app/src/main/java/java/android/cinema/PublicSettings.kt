package java.android.cinema

object PublicSettings {
    val strings = mutableListOf<String>("inception 2010","fringe")
    var isAdult = true

    fun switchAdult(){ isAdult = !isAdult }
}
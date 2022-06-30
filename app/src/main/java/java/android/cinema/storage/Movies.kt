package java.android.cinema.storage

class Movies {

    val comedy = listOf<Movie>(Movie("Тупой и ещё тупее"),
        Movie("Джентельмены удачи"),
        Movie("В джазе только девушки"),
        Movie("Папаши"),
        Movie("Пушки, деньги, два ствола"),
    )
    val fantasy = listOf<Movie>(Movie("День Сурка"), Movie("Солярис"))
    val favorites = listOf<Movie>(Movie("Один дома"), Movie("Крамер против крамера"))

    init {
        comedy[0].setDescription(10,10)
    }

    fun getMovie(indexGenre: Int,indexMovie: Int):Movie{

        when(indexGenre){
            0 -> return comedy[indexMovie]
            1 -> return fantasy[indexMovie]
            2 -> return favorites[indexMovie]
        }
        return Movie("")
    }

}
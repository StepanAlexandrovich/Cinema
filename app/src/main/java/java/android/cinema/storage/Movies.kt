package java.android.cinema.storage

class Movies {

    val comedy = listOf<Movie>(Movie("Тупой и ещё тупее",100),
        Movie("Джентельиены удачи",100),
        Movie("В джазе только девушки",100),
        Movie("Папаши",100),
        Movie("Пушки, деньги, два ствола",100),
    )
    val fantasy = listOf<Movie>(Movie("День Сурка",100), Movie("Солярис",100))
    val favorites = listOf<Movie>(Movie("Один дома",100), Movie("Крамер против крамера",100))

    fun getMovie(indexGenre: Int,indexMovie: Int):Movie{

        when(indexGenre){
            0 -> return comedy[indexMovie]
            1 -> return fantasy[indexMovie]
            2 -> return favorites[indexMovie]
        }
        return Movie("",10)
    }

}
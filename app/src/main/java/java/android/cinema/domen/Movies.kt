package java.android.cinema.domen

class Movies {

    val comedy = mutableListOf<Movie>(
        Movie("Тупой и ещё тупее"),
        Movie("Джентельмены удачи"),
        Movie("В джазе только девушки"),
        Movie("Папаши"),
        Movie("Пушки, деньги, два ствола"),
    )
    val fantasy = mutableListOf<Movie>(Movie("День Сурка"), Movie("Солярис"))
    val animated = mutableListOf<Movie>(Movie("Ну погоди"), Movie("Том и Джери"))

    private val listGenre = mutableListOf(comedy,fantasy,animated)

    init {
        comedy[0].setDescription(10,10)
    }

    fun getMovie(indexGenre: Int,indexMovie: Int):Movie{

        when(indexGenre){
            0 -> return comedy[indexMovie]
            1 -> return fantasy[indexMovie]
            2 -> return animated[indexMovie]
        }
        return Movie("")
    }

    fun addMovie(genre:Int,movie: Movie){
        listGenre[genre].add(movie)
    }

}
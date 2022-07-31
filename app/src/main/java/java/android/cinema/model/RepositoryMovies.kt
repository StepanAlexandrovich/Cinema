package java.android.cinema.model

fun interface RepositoryMovies {
    fun getListMovies(stringGenre:String, callback: MoviesCallback)
}
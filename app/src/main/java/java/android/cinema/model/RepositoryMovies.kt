package java.android.cinema.model

interface RepositoryMovies {
    fun getListMovies(stringGenre:String, callback: MoviesCallback)
}
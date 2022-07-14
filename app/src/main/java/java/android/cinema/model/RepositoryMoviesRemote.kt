package java.android.cinema.model

interface RepositoryMoviesRemote {
    fun getListMovies(callback: LargeSuperCallback)
    fun getListMovies(genre:String, callback: LargeSuperCallback)
}
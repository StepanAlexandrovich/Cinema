package java.android.cinema.listeners

import java.android.cinema.domen.Movie

fun interface OnItemClick {
    fun onItemClick(movie: Movie)
}
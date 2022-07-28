package java.android.cinema.view.view_movie

import android.view.MenuItem
import androidx.fragment.app.FragmentActivity
import java.android.cinema.R
import java.android.cinema.model.room.RoomUtils

class ListenerMenuMovie(val activity: FragmentActivity) {
    fun switchItems(item:MenuItem):Boolean{
        return when (item.itemId) {
            R.id.itemAddInComedy -> {
                RoomUtils.addMovie(0, MovieFragment.currentMovie!!)
                true
            }
            R.id.itemAddInFantasy -> {
                RoomUtils.addMovie(1, MovieFragment.currentMovie!!)
                true
            }
            R.id.itemAddInAnime -> {
                RoomUtils.addMovie(2, MovieFragment.currentMovie!!)
                true
            }
            else -> activity.onOptionsItemSelected(item)
        }
    }
}
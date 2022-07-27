package java.android.cinema.view.details

import android.view.MenuItem
import androidx.fragment.app.FragmentActivity
import java.android.cinema.R
import java.android.cinema.activity.MainActivity
import java.android.cinema.extra.geolocation.GeolocationFragment
import java.android.cinema.extra.phone_numbers.ContentProviderFragment
import java.android.cinema.model.room.RoomUtils
import java.android.cinema.view.utilsToView.Navigation

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
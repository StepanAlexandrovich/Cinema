package java.android.cinema.view.mainscreen

import android.view.MenuItem
import androidx.fragment.app.FragmentActivity
import java.android.cinema.R
import java.android.cinema.activity.MainActivity
import java.android.cinema.extra.geolocation.GeolocationFragment
import java.android.cinema.extra.phone_numbers.ContentProviderFragment
import java.android.cinema.view.utilsToView.Navigation

class MenuMainScreen(val activity: FragmentActivity) {
    fun switchItems(item:MenuItem):Boolean{
        return when (item.itemId) {
            R.id.menu_phones -> {
                Navigation.createFragmentWithBackStack(activity, R.id.container, ContentProviderFragment())
                true
            }
            R.id.menu_geolocation -> {
                Navigation.createFragmentWithBackStack(activity,R.id.container, GeolocationFragment())
                true
            }
            else -> activity.onOptionsItemSelected(item)
        }
    }
}
package java.android.cinema.extra.geolocation

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.android.cinema.activity.MyApp
import java.android.cinema.utils.PrintVisible

class LocationLaunch:Fragment() {
    lateinit var locationManager: LocationManager

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                MyApp.getMyApp(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            locationManager =
                MyApp.getMyApp().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    2000L,
                    0F, locationListener
                )

            }else{
                //locationManager.getLastKnownLocation() // TODO HW
            }
        }
    }

    private fun checkPermission(permission: String) {

        val permResult = ContextCompat.checkSelfPermission(MyApp.getMyApp(), permission)

        if (permResult == PackageManager.PERMISSION_GRANTED) {

            getLocation()

        } else if (shouldShowRequestPermissionRationale(permission)) {
            AlertDialog.Builder(MyApp.getMyApp())
                .setTitle("Доступ к локации")
                .setMessage("Объяснение Объяснение Объяснение Объяснение")
                .setPositiveButton("Предоставить доступ") { _, _ ->
                    permissionRequest(permission)
                }
                .setNegativeButton("Не надо") { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        } else {
            permissionRequest(permission)
        }

    }

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            //PrintVisible.printLong("${location.latitude} ${location.longitude}")
            getAddress(location)

        }

        override fun onProviderDisabled(provider: String) {
            PrintVisible.printShort("onProviderDisabled")
            super.onProviderDisabled(provider)
        }

        override fun onProviderEnabled(provider: String) {
            PrintVisible.printShort("onProviderEnabled")
            super.onProviderEnabled(provider)
        }
    }

    fun getAddress(location: Location) {
        //val geocoder = Geocoder(context)
        //geocoder.getFromLocation(location.latitude, location.longitude, 1)

        PrintVisible.printShort("${location.latitude} ${location.longitude}")

        //Navigation.createFragmentWithBackStack(requireActivity() as AppCompatActivity, R.id.containerMap, MapsFragment.newInstance(location.latitude,location.longitude))

        locationManager.removeUpdates(locationListener)
    }

    private val REQUEST_CODE_LOCATION = 999

    private fun permissionRequest(permission: String) {
        requestPermissions(arrayOf(permission), REQUEST_CODE_LOCATION)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (requestCode == REQUEST_CODE_LOCATION) {
            for (pIndex in permissions.indices) {
                if (permissions[pIndex] == Manifest.permission.ACCESS_FINE_LOCATION
                    && grantResults[pIndex] == PackageManager.PERMISSION_GRANTED
                ) {

                    PrintVisible.printLong("УРА")
                    getLocation()

                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
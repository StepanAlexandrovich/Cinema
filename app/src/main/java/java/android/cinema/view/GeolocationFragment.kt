package java.android.cinema.view

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.*

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.android.cinema.databinding.FragmentGeolocationBinding
import java.android.cinema.utils.PrintVisible
import java.io.IOException


class GeolocationFragment: Fragment(){

    lateinit var locationManager: LocationManager

    private var _binding: FragmentGeolocationBinding? = null
    val binding: FragmentGeolocationBinding get() { return _binding!! }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGeolocationBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener(View.OnClickListener {
            checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    ////////////////////////////////////////////////////////////////

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            locationManager =
                requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    2000L,
                    0F, locationListener
                )

                // FIXME получить один раз координаты
            }else{
                //locationManager.getLastKnownLocation() // TODO HW
            }
        }
    }

    private fun checkPermission(permission: String) {

        val permResult = ContextCompat.checkSelfPermission(requireContext(), permission)

        if (permResult == PackageManager.PERMISSION_GRANTED) {

            getLocation()

        } else if (shouldShowRequestPermissionRationale(permission)) {
            AlertDialog.Builder(requireContext())
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
        val geocoder = Geocoder(context)

        try {
            PrintVisible.printShort("${location.latitude} ${location.longitude}")
            geocoder.getFromLocation(location.latitude, location.longitude, 1)
        }catch (e: IOException){
            PrintVisible.printShort("${e.toString()}")
            e.printStackTrace()

        }

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



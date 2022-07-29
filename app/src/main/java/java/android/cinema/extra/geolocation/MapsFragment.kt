package java.android.cinema.extra.geolocation

import android.location.Address
import android.location.Geocoder
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.android.cinema.R
import java.android.cinema.databinding.FragmentMapsUiBinding
import java.android.cinema.utils.PrintVisible
import java.io.IOException

class MapsFragment : Fragment() {

    companion object {
        var x = 0.0
        var y = 0.0
        fun newInstance(xOut:Double,yOut:Double): MapsFragment {
            x = xOut
            y = yOut
            return MapsFragment()
        }
    }

    private val callback = OnMapReadyCallback { googleMap ->
        val place = LatLng(x, y)
        googleMap.addMarker(MarkerOptions().position(place).title("Place"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(place))
    }

    private var _binding: FragmentMapsUiBinding? = null
    private val binding: FragmentMapsUiBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsUiBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        binding.buttonSearch.setOnClickListener {
            binding.searchAddress.text.toString().let { searchText ->
                val geocoder = Geocoder(requireContext())

                try {
                    val result:List<Address>? = geocoder.getFromLocationName(searchText, 1)

                    if(result == null){ PrintVisible.printShort("такого места не существует") }
                    else{
                        val ln = LatLng(result.first().latitude, result.first().longitude)

                        x = result.first().latitude
                        y = result.first().longitude
                        mapFragment?.getMapAsync(callback)
                    }

                }catch (e: IOException){
                    PrintVisible.printShort("возможно произошла ошибка загрузки с сервера")
                }

            }
        }
    }


}
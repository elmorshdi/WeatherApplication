package com.elmorshdi.weatheraplication.view.ui

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.elmorshdi.weatheraplication.R
import com.elmorshdi.weatheraplication.databinding.FragmentMapsBinding
import com.elmorshdi.weatheraplication.view.util.LatLong
import com.elmorshdi.weatheraplication.view.util.observeOnce
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MapsFragment : Fragment(), OnMapReadyCallback {
    private val viewModel by viewModels<WeatherViewModel>()
    private lateinit var mMap: GoogleMap
    private lateinit var binding: FragmentMapsBinding
    private var location: LatLong = LatLong()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //get provided location from viewModel
        viewModel.currentLocation.observeOnce(viewLifecycleOwner) {
            location.lat = it.latitude
            location.long = it.latitude
        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        binding.floatingActionButton.setOnClickListener {

            //share selected location or current  to another fragment
            val bundle = Bundle()
            bundle.putDouble("latitude", location.lat)
            bundle.putDouble("longitude", location.long)
            it.findNavController().navigate(R.id.action_mapsFragment_to_weatherFragment, bundle)
        }
        //navigate to home without save data like backPress
        binding.backArrow.setOnClickListener {
            it.findNavController().navigate(R.id.action_mapsFragment_to_weatherFragment)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        setAdress(LatLng(location.lat, location.long))
        mMap = googleMap
        mMap.addMarker(
            MarkerOptions()
                .position(LatLng(location.lat, location.long))
                .title("Marker in myLocation")
        )
        mMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(location.lat, location.long),
                12.0f
            )
        )
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE

        mMap.setOnMapLongClickListener {
            location.lat = it.latitude
            location.long = it.longitude
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(it))
            setAdress(it)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 14.0f))
        }
    }

    //get adress from latLong and display to ui
    private fun setAdress(it: LatLng) {
        val gcd = Geocoder(context, Locale.getDefault())
        val addresses: List<Address> = gcd.getFromLocation(it.latitude, it.longitude, 1)
        if (addresses.isNotEmpty()) {
            binding.cityName.text = addresses[0].locality
        } else {
            // do your stuff
        }
    }


}
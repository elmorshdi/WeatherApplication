package com.elmorshdi.weatheraplication.view.ui

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.elmorshdi.weatheraplication.R
import com.elmorshdi.weatheraplication.databinding.FragmentWeatherBinding
import com.elmorshdi.weatheraplication.domain.weather.WeatherByDay
import com.elmorshdi.weatheraplication.domain.weather.WeatherData
import com.elmorshdi.weatheraplication.domain.weather.WeatherInfo
import com.elmorshdi.weatheraplication.view.adapter.DaysAdapter
import com.elmorshdi.weatheraplication.view.adapter.HoursAdapter
 import com.elmorshdi.weatheraplication.view.util.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class WeatherFragment : Fragment(),DaysAdapter.Interaction {
     private val viewModel by viewModels<WeatherViewModel>()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var binding: FragmentWeatherBinding
    private  var location: LatLong = LatLong()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val language=if(Locale.getDefault().displayLanguage.toString() == "العربية") "ar" else "en"
       val latitude=arguments?.getDouble("latitude",0.0)
        val longitude=arguments?.getDouble("longitude",0.0)

        Log.d("tag",Locale.getDefault().displayLanguage.toString())
        if (
            requireContext().checkForInternet()
        ){
            Log.d("tag","online")

            if (latitude==null||longitude==null||latitude==0.0||longitude==0.0){

                permissionLauncher = registerForActivityResult(
                    ActivityResultContracts.RequestMultiplePermissions()
                ) {
                    Log.d("tag","2222")

                    loadWeatherInfo(language )
                }
                permissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                    )
                )
            }
            else {
                location.lat= latitude
                location.long= longitude
                loadWeatherInfo(language,location)
            }
            binding.locationIcon.setOnClickListener {
                val action= WeatherFragmentDirections.actionWeatherFragmentToMapsFragment()
                it.findNavController().navigate(action)
            }
            binding.reloadButton.setOnClickListener {
                loadWeatherInfo(language)
            }
        }else{
            //
            Log.d("tag","offline")

            viewModel.getCashedData()
        }



        lifecycleScope.launchWhenCreated {
            viewModel.mainUiState.collect { event ->
                when (event) {
                    is Status.LOADING ->{
                        showProgress(true)
                    }
                    is Status.ERROR -> {
                        viewModel.error.observeOnce(viewLifecycleOwner){
                            setError(it)
                        }
                    }
                    is Status.SUCCESS -> {
                        showProgress(false)
                        viewModel.weatherInfo.observeOnce(viewLifecycleOwner) {
                            setupViews(it)
                        }

                    }

                    else -> UInt
                }
            }
        }

    }

    private fun setError(it: String?) {
        binding.errorText.visibility=View.VISIBLE
        binding.progressCircular.visibility = View.GONE
        binding.currentInfo.visibility = View.GONE
        binding.errorText.text=it!!
    }

    private fun showProgress(b: Boolean) {
        if (b){
            binding.errorText.visibility=View.GONE
            binding.progressCircular.visibility = View.VISIBLE
            binding.currentInfo.visibility = View.GONE
        }else{
            binding.errorText.visibility=View.GONE
            binding.progressCircular.visibility = View.GONE
            binding.currentInfo.visibility = View.VISIBLE
        }

    }

    private fun loadWeatherInfo(language:String) {

        viewModel.loadWeatherInfo( requireContext().resources.getString(R.string.api_key), language, "metric")
    }
    private fun loadWeatherInfo(language:String, location: LatLong) {
        viewModel.loadWeatherInfo(location,requireContext().resources.getString(R.string.api_key), language, "metric")
    }
    private fun setupViews(it: WeatherInfo) {
        binding.weather=it.currentWeatherData
        binding.cityName.text=it.cityName
        setupHourRV(it.weatherDataPerDate[0].weather)

        val daysAdapter =DaysAdapter(this,requireContext())
        daysAdapter.submitList(it.weatherDataPerDate)
        binding.dateRv.adapter = daysAdapter
    }

    private fun setupHourRV(it: List<WeatherData>) {
        val hoursAdapter = HoursAdapter()
        hoursAdapter.submitList(it)
        binding.hourRv.adapter = hoursAdapter
    }

    override fun onItemSelected(weatherByDay: WeatherByDay) {
        setupHourRV(weatherByDay.weather)
    }

}



package com.elmorshdi.weatheraplication.view.ui

import android.Manifest
import android.content.res.Resources
import android.os.Bundle
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
class WeatherFragment : Fragment(), DaysAdapter.Interaction {
    private val viewModel by viewModels<WeatherViewModel>()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var binding: FragmentWeatherBinding
    private var location: LatLong = LatLong()
    private var unit :WeatherUnit=WeatherUnit.Metric
    private lateinit var language :String

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
        language=if (Locale.getDefault().displayLanguage.toString() == "العربية") "ar" else "en"

        val latitude = arguments?.getDouble("latitude", 0.0)
        val longitude = arguments?.getDouble("longitude", 0.0)

        if (requireContext().checkForInternet()) {
            if (latitude == null || longitude == null || latitude == 0.0 || longitude == 0.0) {
                permissionLauncher = registerForActivityResult(
                    ActivityResultContracts.RequestMultiplePermissions()
                ) {
                    loadWeatherInfo(language,unit)
                }
                permissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                    )
                )
            } else {
                location.lat = latitude
                location.long = longitude
                loadWeatherInfo(language, location,unit)
            }
            binding.locationIcon.setOnClickListener {
                val action = WeatherFragmentDirections.actionWeatherFragmentToMapsFragment()
                it.findNavController().navigate(action)
            }
            binding.reloadButton.setOnClickListener {
                loadWeatherInfo(language ,unit)
            }
        } else {

            binding.locationIcon.setOnClickListener {
              requireContext().toast("Connect to internet to open Maps ")
            }
            binding.reloadButton.setOnClickListener {
                requireContext().toast("Connect to internet to Reload")
            }
            viewModel.getCashedData()
        }

        binding.celsius.setOnClickListener {
            changeUnitTo(WeatherUnit.Metric)
            binding.celsius.setTextColor(requireContext().resources.getColor(R.color.white, Resources.getSystem().newTheme()))
            binding.fahrenheit.setTextColor(requireContext().resources.getColor(R.color.gray, Resources.getSystem().newTheme()))
        }
        binding.fahrenheit.setOnClickListener {
            changeUnitTo(WeatherUnit.Standard)
            binding.fahrenheit.setTextColor(requireContext().resources.getColor(R.color.white, Resources.getSystem().newTheme()))
            binding.celsius.setTextColor(requireContext().resources.getColor(R.color.gray, Resources.getSystem().newTheme()))
        }
        lifecycleScope.launchWhenCreated {
            viewModel.mainUiState.collect { event ->
                when (event) {
                    is Status.LOADING -> {
                        showProgress(true)
                    }
                    is Status.ERROR -> {
                        viewModel.error.observeOnce(viewLifecycleOwner) {
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

    private fun changeUnitTo(weatherUnit: WeatherUnit) {
        if (unit!=weatherUnit){
            loadWeatherInfo(language = language , weatherUnit=weatherUnit )
            unit=weatherUnit

        }

    }

    private fun setError(it: String?) {
        binding.errorText.visibility = View.VISIBLE
        binding.progressCircular.visibility = View.GONE
        binding.currentInfo.visibility = View.GONE
        binding.errorText.text = it!!
    }

    private fun showProgress(b: Boolean) {
        if (b) {
            binding.errorText.visibility = View.GONE
            binding.progressCircular.visibility = View.VISIBLE
            binding.currentInfo.visibility = View.GONE
        } else {
            binding.errorText.visibility = View.GONE
            binding.progressCircular.visibility = View.GONE
            binding.currentInfo.visibility = View.VISIBLE
        }

    }

    private fun loadWeatherInfo(language: String,weatherUnit: WeatherUnit) {
        viewModel.updateCurrentLocation()
        viewModel.loadWeatherInfo(
            requireContext().resources.getString(R.string.api_key),
            language,
            weatherUnit.type
        )
    }

    private fun loadWeatherInfo(language: String, location: LatLong,weatherUnit: WeatherUnit) {
        viewModel.updateCurrentLocation()
        viewModel.loadWeatherInfo(
            location,
            requireContext().resources.getString(R.string.api_key),
            language,
            weatherUnit.type
        )
    }

    private fun setupViews(it: WeatherInfo) {
        binding.weather = it.currentWeatherData
        binding.cityName.text = it.cityName
        setupHourRV(it.weatherDataPerDate[0].weather)

        val daysAdapter = DaysAdapter(this, requireContext())
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
sealed class WeatherUnit(val type:String){
    object Metric:WeatherUnit(type="metric")
    object Standard:WeatherUnit(type="standard")
}
}



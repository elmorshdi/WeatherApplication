package com.elmorshdi.weatheraplication.view.ui

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmorshdi.weatheraplication.domain.location.LocationTracker
import com.elmorshdi.weatheraplication.domain.repository.WeatherRepository
import com.elmorshdi.weatheraplication.domain.util.Resource
import com.elmorshdi.weatheraplication.domain.weather.WeatherInfo
import com.elmorshdi.weatheraplication.view.util.LatLong
import com.elmorshdi.weatheraplication.view.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {
    val mainUiState: StateFlow<Status>
        get() = _mainUiState
    private val _mainUiState = MutableStateFlow<Status>(Status.Empty)


    val weatherInfo: LiveData<WeatherInfo>
        get() = _weatherInfo
    private val _weatherInfo: MutableLiveData<WeatherInfo> = MutableLiveData()


    val error: LiveData<String>
        get() = _error
    private val _error: MutableLiveData<String> = MutableLiveData()


    val currentLocation: LiveData<Location>
        get() = _currentLocation
    private val _currentLocation: MutableLiveData<Location> = MutableLiveData()


    fun loadWeatherInfo(
        location: LatLong,
        ApiKey: String,
        language: String,
        unit: String
    ) {
        viewModelScope.launch {
            _mainUiState.value = Status.LOADING

                when (val result =
                    repository.getWeatherData(
                        lat = location.lat,
                        long = location.long,
                        key = ApiKey,
                        lang = language,
                        unit = unit
                    )) {
                    is Resource.Success<*> -> {
                        _mainUiState.value = Status.SUCCESS
                         _weatherInfo.value=result.data!!

                    }
                    is Resource.Error<*> -> {
                        _mainUiState.value = Status.ERROR
                        _error.value = result.message!!
                    }
                    else -> {}
                }
            }
        }

    fun loadWeatherInfo(
          ApiKey: String,
        language: String,
        unit: String
    ) {
        viewModelScope.launch {
            _mainUiState.value = Status.LOADING
            locationTracker.getCurrentLocation()?.let { location ->

            when (val result =
                repository.getWeatherData(
                    lat = location.latitude,
                    long = location.longitude,
                    key = ApiKey,
                    lang = language,
                    unit = unit
                )) {
                is Resource.Success<*> -> {
                    _mainUiState.value = Status.SUCCESS
                     _weatherInfo.value=result.data!!

                }
                is Resource.Error<*> -> {
                    _mainUiState.value = Status.ERROR
                    _error.value = result.message!!
                }
                else -> {}
            }
        }
        }
    }

    private fun getCurrentLocation() {
        viewModelScope.launch {
            locationTracker.getCurrentLocation()?.let { location ->
                _currentLocation.value = location
            }
        }
    }

    fun updateCurrentLocation(location: Location){
       _currentLocation.value = location
   }
    fun updateCurrentLocation(){
        getCurrentLocation()
    }
    init {
        getCurrentLocation()
    }
}

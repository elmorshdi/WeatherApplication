package com.elmorshdi.weatheraplication.view.ui.sharedviewmodel

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmorshdi.weatheraplication.data.cachedata.model.DbModel
import com.elmorshdi.weatheraplication.data.mappers.mapToDbModel
import com.elmorshdi.weatheraplication.data.mappers.mapToWeatherInfo
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
                    _weatherInfo.value = result.data?.mapToWeatherInfo()
                    deleteCashed()
                    result.data?.let { insertToDB(it.mapToDbModel()) }

                }
                is Resource.Error<*> -> {
                    _mainUiState.value = Status.ERROR
                    _error.value = result.message!!
                }
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
                if (location is Resource.Error) {
                    _mainUiState.value = Status.ERROR
                    _error.value = location.message!!
                } else if (location is Resource.Success) {

                    when (val result = repository.getWeatherData(
                        lat = location.data?.latitude!!,
                        long = location.data.longitude,
                        key = ApiKey,
                        lang = language,
                        unit = unit
                    )
                    ) {
                        is Resource.Success<*> -> {
                            _mainUiState.value = Status.SUCCESS
                            _weatherInfo.value = result.data?.mapToWeatherInfo()
                            deleteCashed()
                            result.data?.let { insertToDB(it.mapToDbModel()) }


                        }
                        is Resource.Error<*> -> {
                            _mainUiState.value = Status.ERROR
                            _error.value = result.message!!
                        }
                    }
                }
            }
        }
    }


    private fun getCurrentLocation() {
        viewModelScope.launch {

            locationTracker.getCurrentLocation()?.let { location ->
                when (location) {
                    is Resource.Success<*> -> {
                        _currentLocation.value = location.data!!

                    }
                    is Resource.Error<*> -> {
                        _mainUiState.value = Status.ERROR
                        _error.value = "Failed to determine your location, open GPS and reload  "
                    }
                }


            }


        }
    }

    //------------------------------CashedData---------------------------------------------
    fun getCashedData() {
        _mainUiState.value = Status.LOADING
        viewModelScope.launch {
            try {
                repository.getCashedWeatherInfo().collect {
//                    Log.d("tag",it[0].cityName+it[0].weatherDataPerDate.toString())

                    if (it.isEmpty()) {
                        _mainUiState.value = Status.ERROR
                        _error.value = "Connect To Internet"
                    }
                    it.forEach { e ->
                        _weatherInfo.postValue(e.mapToWeatherInfo())
                        Log.d("tag", e.toString())
                    }
                    _mainUiState.value = Status.SUCCESS

                }
            } catch (e: Exception) {
                _mainUiState.value = Status.ERROR

                Log.d("tag", e.message.toString())
                _error.value = "Connect To Internet"
            }

        }

    }

    private fun deleteCashed() {
        viewModelScope.launch {
            try {
                repository.deleteCashedWeatherInfo()

            } catch (e: Exception) {
                _mainUiState.value = Status.ERROR
                _error.value = "Connect To Internet"
            }

        }

    }

    private fun insertToDB(weatherInfo: DbModel) {

        viewModelScope.launch {
            try {
                repository.addWeatherInfoToDB(weatherInfo)

            } catch (e: Exception) {
                _mainUiState.value = Status.ERROR
                _error.value = "Connect To Internet"
            }

        }
    }

    //End------------------------------CashedData---------------------------------------------
    fun updateCurrentLocation(location: Location) {
        _currentLocation.value = location
    }

    fun updateCurrentLocation() {
        getCurrentLocation()
    }

}

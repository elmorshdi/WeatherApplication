package com.elmorshdi.weatheraplication.data.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
 import com.elmorshdi.weatheraplication.domain.location.LocationTracker
import com.elmorshdi.weatheraplication.domain.util.Resource
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class DefaultLocationTracker @Inject constructor(
    private val locationClient:FusedLocationProviderClient,
    private val application: Application
) : LocationTracker {
    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation():Resource<Location>? {
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_FINE_LOCATION
        )== PackageManager.PERMISSION_GRANTED
        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )== PackageManager.PERMISSION_GRANTED
        val locationManager = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!hasAccessFineLocationPermission || !hasAccessCoarseLocationPermission || !isGpsEnabled){
            return Resource.Error("Failed to determine your location, open GPS and reload")
        }
        return suspendCancellableCoroutine { cont ->
            locationClient.lastLocation.apply {
                if(isComplete) {
                    if(isSuccessful) {
                        cont.resume(Resource.Success(result))
                    } else {
                        cont.resume(Resource.Error("Failed to determine your location, open GPS and reload  "))
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    cont.resume(Resource.Success(it))
                }
                addOnFailureListener {
                    cont.resume(Resource.Error(it.message.toString()))
                }
                addOnCanceledListener {
                    cont.cancel()

                }
            }
        }
    }
}
package com.elmorshdi.weatheraplication.domain.location

import android.location.Location
import com.elmorshdi.weatheraplication.domain.util.Resource

interface LocationTracker {
    suspend fun getCurrentLocation(): Resource<Location>?
}
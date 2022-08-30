package com.elmorshdi.weatheraplication.view.util

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LatLong(
    var lat: Double = 0.0,
    var long: Double = 0.0
) :  Parcelable
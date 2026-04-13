package com.dexdiary.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

class LocationHelper(private val context: Context) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocationName(): String? {
        return withContext(Dispatchers.Default) {
            try {
                val locationTask = fusedLocationClient.lastLocation
                val location = Tasks.await(locationTask)
                if (location == null) return@withContext null
                
                val geocoder = Geocoder(context, Locale.getDefault())
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                if (addresses?.isNotEmpty() == true) {
                    val address = addresses[0]
                    "${address.locality}, ${address.countryName}"
                } else null
            } catch (e: Exception) {
                null
            }
        }
    }
}

package com.example.pronadjicvecaru.services

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.pronadjicvecaru.R
import com.example.pronadjicvecaru.data.Location
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LocationTrackerService: Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: LocationClient

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        start()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {

        locationClient
            .getLocationUpdates(1000L)
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                Location.Set(location.latitude, location.longitude)

            }
            .launchIn(serviceScope)

        Log.d("LOCATION SERVICE", "Service started.")
    }

    private fun stop() {
        Log.d("LOCATION SERVICE", "Service stopped.")
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    override fun stopService(name: Intent?): Boolean {
        stop()
        return super.stopService(name)
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}
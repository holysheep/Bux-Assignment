package com.assignment.core.infrastructure

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

@Suppress("DEPRECATION")
class DeviceConnectivityState(private val context: Context) {

    private val connectivityFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)

    fun changes() = callbackFlow<Boolean> {
        val broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                offer(isConnected())
            }
        }

        context.registerReceiver(broadcastReceiver, connectivityFilter)

        awaitClose {
            context.unregisterReceiver(broadcastReceiver)
        }
    }

    @SuppressLint("MissingPermission")
    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> { return true }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> { return true }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> { return true }
                }
            }
        } else {
            try {
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                    return true
                }
            } catch (e: Exception) {
            }
        }
        return false
    }
}
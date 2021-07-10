package com.weather.test.service

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest

class NetworkService(
    connectivityManager: ConnectivityManager
) {
    var isNetworkEnable: Boolean = false
    private set

    init {
        val networkRequest = NetworkRequest.Builder().build()

        connectivityManager.registerNetworkCallback(networkRequest,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    this@NetworkService.isNetworkEnable = true
                }

                override fun onUnavailable() {
                    this@NetworkService.isNetworkEnable = false
                }
            })
    }
}

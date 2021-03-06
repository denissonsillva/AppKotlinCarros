package br.com.livroandroid.carros.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import androidx.annotation.RequiresApi

object AndroidUtils {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networks = connectivity.allNetworks
        return networks
            .map { connectivity.getNetworkInfo(it) }
            .any { it?.state == NetworkInfo.State.CONNECTED };
    }
}
package com.example.newstaskapp.view.main.utils.netWork

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object InternetStateKotlin {
    var cm: ConnectivityManager? = null
    fun isConnected2(context: Context): Boolean {
        try {
            cm =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        } catch (e: NullPointerException) {
        }
        val activeNetwork = cm!!.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}

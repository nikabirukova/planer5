package com.example.planer.data.deeplink

import android.net.Uri
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DeepLinkRouter {

    private val _currentDestination = MutableStateFlow<Destination>(Destination.Home)
    val currentDestination: StateFlow<Destination> = _currentDestination

    fun parseURL(url: String): Destination? {
        return try {
            val uri = Uri.parse(url)

            val scheme = uri.scheme
            val host = uri.host
            val path = uri.pathSegments

            // myapp://
            if (scheme == "myapp") {
                return when (host) {

                    "tasks" -> {
                        if (path.size == 1) {
                            Destination.Detail(path[0])
                        } else {
                            val filter = uri.getQueryParameter("filter")
                            Destination.Catalog(filter)
                        }
                    }

                    "invite" -> {
                        if (path.size == 1) {
                            Destination.Invite(path[0])
                        } else null
                    }

                    else -> null
                }
            }

            // https://myapp.com
            if (scheme == "https" && host == "myapp.com") {
                return when {

                    path.size == 2 && path[0] == "tasks" ->
                        Destination.Detail(path[1])

                    path.size == 1 && path[0] == "tasks" ->
                        Destination.Catalog(uri.getQueryParameter("filter"))

                    else -> null
                }
            }

            null

        } catch (e: Exception) {
            null
        }
    }

    fun handle(url: String) {
        val destination = parseURL(url)
        if (destination != null) {
            navigate(destination)
        }
    }

    fun navigate(destination: Destination) {
        _currentDestination.value = destination
    }
}
package com.example.planer.data.deeplink

sealed class Destination {
    object Home : Destination()
    data class Detail(val id: String) : Destination()
    data class Catalog(val filter: String?) : Destination()
    data class Invite(val token: String) : Destination()
    object Notifications : Destination()
}
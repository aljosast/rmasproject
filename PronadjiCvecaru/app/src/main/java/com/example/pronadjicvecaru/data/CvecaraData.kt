package com.example.pronadjicvecaru.data

import android.net.Uri
import com.google.android.gms.maps.model.LatLng

data class CvecaraData (

    var Name: String = "",
    var Number:String = "",
    var RadnoVreme: String ="",
    var Isporuka: Boolean = false,
    var About: String = "",
    var Adresa: String = "",
    var Ocena: Double = 0.0,
    var ProfilePicture: String = "",
    var Pictures: List<String> = emptyList(),
    var Lat: Double = 0.0,
    var Lng: Double = 0.0,
    var UserID: String = ""
)
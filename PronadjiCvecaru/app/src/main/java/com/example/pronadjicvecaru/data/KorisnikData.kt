package com.example.pronadjicvecaru.data

import android.net.Uri
import androidx.core.net.toUri

data class KorisnikData (

        var Name:String = "",
        var Lastname:String = "",
        var Username:String = "",
        var Email:String = "",
        var YearsOld:String = "",
        var Points: Int = 0,
        var Picture: String = ""
)
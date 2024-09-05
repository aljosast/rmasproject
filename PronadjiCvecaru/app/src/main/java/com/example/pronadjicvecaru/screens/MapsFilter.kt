package com.example.pronadjicvecaru.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pronadjicvecaru.ViewModels.MainVM
import com.example.pronadjicvecaru.data.CvecaraData
import com.example.pronadjicvecaru.data.Location
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapsFilter(main: MainVM, goTo: (String) -> Unit) {

    var poss = remember { mutableStateOf(LatLng(Location.Getx(), Location.Gety())) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(poss.value, 10f)
    }
    var uiSettings by remember { mutableStateOf(MapUiSettings()) }
    var properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }
    var filter = remember { mutableStateOf(false)}
    Column {
        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1f),
            //.background(MaterialTheme.colorScheme.primary),
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
            Text(text = "Prikazi filtrirano", fontSize = 22.sp)
            Checkbox(checked = filter.value, onCheckedChange = { b -> filter.value = b })
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MaterialTheme.colorScheme.inverseSurface, RectangleShape)
        ) {}
        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.91f),
            cameraPositionState = cameraPositionState,
            properties = properties,
            uiSettings = uiSettings
        ) {
            Marker(
                state = MarkerState(position = poss.value),
                title = "Vasa trenutna lokacija",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
            )
            if (filter.value) {
                for (c in main.cvecarefiltered.value) {
                    Marker(
                        state = MarkerState(position = LatLng(c.Lat, c.Lng)),
                        title = c.Name,
                        snippet = c.Adresa
                    )
                }
            }
            else
            {
                for (c in main.cvecare.value) {
                    Marker(
                        state = MarkerState(position = LatLng(c.Lat, c.Lng)),
                        title = c.Name,
                        snippet = c.Adresa
                    )
                }
            }
        }
        Navbar(page = 3, goTo = goTo, main = main)
    }
}
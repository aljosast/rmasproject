package com.example.pronadjicvecaru.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.pronadjicvecaru.data.CvecaraData
import com.example.pronadjicvecaru.data.Location
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMapOptions
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
fun Maps(cvecara: CvecaraData, createOrView: Boolean) {

    var poss = remember { mutableStateOf(LatLng(cvecara.Lat, cvecara.Lng))}

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(poss.value, 20f)
    }
    var uiSettings by remember { mutableStateOf(MapUiSettings()) }
    var properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = properties,
        uiSettings = uiSettings,
        onMapClick = {position ->
            if(createOrView) {
                cvecara.Lat = position.latitude
                cvecara.Lng = position.longitude
                poss.value = position
            }
        }
    ){
        Marker(
            state = MarkerState(position = poss.value),
            title = cvecara.Name,
            snippet = cvecara.Adresa
        )
        Marker(
            state = MarkerState(position = LatLng(Location.Getx(),Location.Gety())),
            title = "Vasa trenutna lokacija",
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
        )

    }
}
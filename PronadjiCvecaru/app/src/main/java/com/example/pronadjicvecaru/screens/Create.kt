package com.example.pronadjicvecaru.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pronadjicvecaru.ViewModels.MainVM
import com.example.pronadjicvecaru.ViewModels.SignUpVM
import com.example.pronadjicvecaru.data.CvecaraData

@Composable
fun Create(goTo: (String) -> Unit, main: MainVM){
    Column {
        Column(modifier = Modifier
            .fillMaxHeight(0.92f)) {
            Text(
                text = "Dodajte vasu cvecaru",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(0.dp, 5.dp, 0.dp, 20.dp),
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                ProfilePicturee(main = main)
                Column(modifier = Modifier.padding(5.dp)) {
                    OutlinedTextField(
                        value = main.cvecara.value.Name,
                        onValueChange = { main.cvecara.value = main.cvecara.value.copy(Name = it) },
                        label = {
                            Text(
                                text = "Naziv cvecare"
                            )
                        },
                        modifier = Modifier
                            .height(60.dp)
                            .width(200.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    OutlinedTextField(
                        value = main.cvecara.value.Number,
                        onValueChange = {
                            main.cvecara.value = main.cvecara.value.copy(Number = it)
                        },
                        label = {
                            Text(
                                text = "Broj telefona"
                            )
                        },
                        modifier = Modifier
                            .height(60.dp)
                            .width(200.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
            OutlinedTextField(value = main.cvecara.value.Adresa,
                onValueChange = { main.cvecara.value = main.cvecara.value.copy(Adresa = it) },
                label = {
                    Text(
                        text = "Adresa"
                    )
                },
                modifier = Modifier
                    .padding(24.dp, 5.dp)
                    .height(60.dp)
                    .align(Alignment.CenterHorizontally)
            )
            OutlinedTextField(value = main.cvecara.value.RadnoVreme,
                onValueChange = { main.cvecara.value = main.cvecara.value.copy(RadnoVreme = it) },
                label = {
                    Text(
                        text = "Radno vreme"
                    )
                },
                modifier = Modifier
                    .padding(24.dp, 5.dp)
                    .height(60.dp)
                    .align(Alignment.CenterHorizontally)
            )
            OutlinedTextField(value = main.cvecara.value.About,
                onValueChange = { main.cvecara.value = main.cvecara.value.copy(About = it) },
                label = {
                    Text(
                        text = "O cvecari"
                    )
                },
                modifier = Modifier
                    .padding(24.dp, 5.dp)
                    .height(200.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Row(
                modifier = Modifier
                    .padding(16.dp, 5.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Dostava:", color = MaterialTheme.colorScheme.onBackground)
                    Spacer(modifier = Modifier.width(5.dp))
                    Checkbox(
                        checked = main.cvecara.value.Isporuka,
                        onCheckedChange = {
                            main.cvecara.value = main.cvecara.value.copy(Isporuka = it)
                        }
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Dodajte na mapi:", color = MaterialTheme.colorScheme.onBackground)
                    Spacer(modifier = Modifier.width(5.dp))
                    IconButton(onClick = { goTo("Maps") }) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Add on map"
                        )
                    }
                }
            }
            ProfileCreationScreen(main)
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                val c = LocalContext.current
                Button(
                    onClick = { main.CreateFS(c) }, modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RectangleShape
                        )
                ) {
                    Text(text = "Dodaj cvecaru")
                }
            }
        }
        Column {
            Navbar(page = 2, goTo, main)
        }
    }
}

@Composable
fun ImagePicker(onImageSelected: (Uri?) -> Unit) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null)
            onImageSelected(uri)
    }

    Box(modifier = Modifier
        .width(100.dp)
        .height(100.dp)
        .clickable(onClick = {
            launcher.launch("image/*")
        }), contentAlignment = Alignment.Center){
        Icon(imageVector = Icons.Default.Add, contentDescription = "AddPicture", modifier = Modifier.fillMaxSize(0.5f))
    }
}

@Composable
fun ProfileCreationScreen(main: MainVM) {
    var picturelist = remember{ mutableStateOf(main.cvecara.value.Pictures)}
        Row(modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())) {
            ImagePicker(onImageSelected = { uri ->
                main.cvecara.value = main.cvecara.value.copy(Pictures = main.cvecara.value.Pictures + uri.toString())
                picturelist.value = main.cvecara.value.Pictures
            })
            for (p in picturelist.value) {
                Box(modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    ) {
                    AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                        .data(p).diskCacheKey(p + System.currentTimeMillis()).memoryCacheKey(p + System.currentTimeMillis()).build(),
                        contentDescription = "", modifier = Modifier.fillMaxSize())
                    }
            }
        }
}

@Composable
fun ProfilePicturee(main: MainVM) {

    PPPickerr(onImageSelected = { uri ->
        main.cvecara.value = main.cvecara.value.copy(ProfilePicture = uri.toString())
    }, main)
}

@Composable
fun PPPickerr(onImageSelected: (Uri?) -> Unit, main: MainVM) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null)
            onImageSelected(uri)
    }
    Box(modifier = Modifier
        .width(100.dp)
        .height(100.dp)
        .padding(0.dp, 0.dp, 20.dp, 0.dp)) {
        if (main.cvecara.value.ProfilePicture != "")
            AsyncImage(
                model = main.cvecara.value.ProfilePicture.toUri(),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(onClick = { launcher.launch("image/*"); main.checkProfilePicture.value = true })
            )
        else{
            Icon(imageVector = Icons.Default.AddCircle, contentDescription = "AddPicture", modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = { launcher.launch("image/*"); main.checkProfilePicture.value = true }))
        }
    }
}

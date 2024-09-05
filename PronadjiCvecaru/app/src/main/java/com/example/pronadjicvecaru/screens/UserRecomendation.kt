package com.example.pronadjicvecaru.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pronadjicvecaru.ViewModels.FlowerShopInfoVM
import com.example.pronadjicvecaru.ViewModels.SignUpVM

@Composable
fun UserRecomendation(back: () -> Unit, fsi: FlowerShopInfoVM){
    val c = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { back() }) {
                Icon(imageVector = Icons.Default.ArrowBack, "Go back")
            }
            Text(text = "Predlozi/Slika", fontSize = 20.sp, color = Color.White)
            Text(text = "          ")
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
            .padding(5.dp)
            .border(1.dp, MaterialTheme.colorScheme.onBackground, RectangleShape),
            horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
            UserRecomendationPicture(fsi = fsi)
        }
        OutlinedTextField(value = fsi.recomendation.value, onValueChange = { fsi.recomendation.value = it }, label = { Text(
            text = "Unesite predlog"
        )}, modifier = Modifier
            .height(170.dp)
            .fillMaxWidth()
            .padding(5.dp))
        Spacer(modifier = Modifier.fillMaxHeight(0.7f))
        Button(onClick = { fsi.CreateRecomendation(c) }, modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(), shape = RectangleShape) {
            Text(text = "Dodaj")
        }
    }
}

@Composable
fun UserRecomendationPicture(fsi: FlowerShopInfoVM) {
    UserPicturePicker(onImageSelected = { uri ->
        fsi.userpicture.value = uri.toString() }, fsi)
}

@Composable
fun UserPicturePicker(onImageSelected: (Uri?) -> Unit, fsi: FlowerShopInfoVM) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null)
            onImageSelected(uri)
    }
        if (fsi.userpicture.value != "")
            AsyncImage(
                model = fsi.userpicture.value,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(onClick = { launcher.launch("image/*") })
            )
        else{
            Icon(imageVector = Icons.Default.Add, contentDescription = "AddPicture", modifier = Modifier
                .fillMaxSize(0.5f)
                .clickable(onClick = { launcher.launch("image/*") }))
        }
}
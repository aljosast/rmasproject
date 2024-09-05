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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.example.pronadjicvecaru.ViewModels.FlowerShopInfoVM
import com.example.pronadjicvecaru.ViewModels.MainVM
import com.example.pronadjicvecaru.ViewModels.SignUpVM
import java.time.format.TextStyle

@Composable
fun FlowerShopInfo(goTo: (String) -> Unit, fsi: FlowerShopInfoVM, main: MainVM){
    var changedatadialog = remember { mutableStateOf(false) }
    var pic = remember { mutableStateOf(Uri.EMPTY)}
    var c = LocalContext.current
    if(changedatadialog.value)
        PictureDialog(Close = { changedatadialog.value = false }, picture = pic.value)
    Column {
        Column(modifier = Modifier
            .fillMaxHeight(0.92f)
            .verticalScroll(rememberScrollState())) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.inverseSurface, RectangleShape)
            ) {}
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { goTo("UserRecomendation") }, modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RectangleShape
                        )
                ) {
                    Text(text = "Dodaj sliku/predlog")
                }
                Button(
                    onClick = { goTo("Reviews") }, modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RectangleShape
                        )
                ) {
                    Text(text = "Recenzije")
                }
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 50.dp), horizontalArrangement = Arrangement.SpaceAround) {
                Column {
                    Text(text = fsi.cvecarainfo.value.Name, fontSize = 25.sp)
                    Text(text = fsi.cvecarainfo.value.Adresa, fontSize = 25.sp)
                    Text(text = fsi.cvecarainfo.value.Number, fontSize = 20.sp, modifier = Modifier.alpha(0.6f))
                    if(fsi.cvecarainfo.value.Isporuka)
                        Text(text = "Isporuka: Da", fontSize = 25.sp)
                    else
                        Text(text = "Isporuka: Ne", fontSize = 25.sp)
                    Button(onClick = { goTo("Maps1") }) {
                        Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Lokacija cvecare")
                        Text(text = "Lokacija")
                    }
                }
                    Box(modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .clickable(onClick = {
                            changedatadialog.value = true; pic.value =
                            fsi.cvecarainfo.value.ProfilePicture.toUri()
                        })
                    ) {
                        AsyncImage(
                            model = fsi.cvecarainfo.value.ProfilePicture,
                            contentDescription = "Profile picture",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(MaterialTheme.colorScheme.inverseSurface, RectangleShape)
            ) {}
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 20.dp)) {
                Text(text = fsi.cvecarainfo.value.RadnoVreme, fontSize = 20.sp)
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 20.dp)) {
                Text(text = "O cvecari: " + fsi.cvecarainfo.value.About, fontSize = 16.sp, modifier = Modifier.alpha(0.8f))
            }
            Text(text = "Slike", fontSize = 20.sp, modifier = Modifier.padding(10.dp, 20.dp))
            for (i in 0..fsi.cvecarainfo.value.Pictures.size/4 )
                Row(modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp, 20.dp)
                    ) {
                    for(p in 0..3){
                        if(i * 4 + p < fsi.cvecarainfo.value.Pictures.size)
                            Box(modifier = Modifier
                                .width(100.dp)
                                .height(100.dp)
                                .clickable(onClick = {
                                    changedatadialog.value = true; pic.value =
                                    fsi.cvecarainfo.value.Pictures[i * 4 + p].toUri()
                                })){
                                AsyncImage(model = fsi.cvecarainfo.value.Pictures[i * 4 + p], contentDescription = "Pictures of flowershop")
                            }
                    }
                }
            Text(text = "Predlozi korisnika", fontSize = 20.sp, modifier = Modifier.padding(10.dp, 20.dp))
            for(r in fsi.recomendations.value){
                Row(modifier = Modifier
                    .fillMaxWidth()

                    //.height(120.dp)
                    .padding(10.dp)
                    .border(1.dp,MaterialTheme.colorScheme.onBackground, RectangleShape)
                )

                {
                    Box(modifier = Modifier
                        .height(100.dp)
                        .width(100.dp)
                        .padding(0.dp, 0.dp, 10.dp, 0.dp)
                    ){
                        AsyncImage(model = r.Picture, contentDescription = "User picture", modifier = Modifier.fillMaxSize().
                        clickable(onClick = { changedatadialog.value = true; pic.value =
                        r.Picture.toUri()}))
                    }
                    Column {
                        Text(text = r.Username, fontSize = 22.sp, style = MaterialTheme.typography.titleMedium)
                        Text(text = r.Recomendation)
                    }
                }
            }
            if(fsi.cvecarainfo.value.UserID == fsi.id.value){
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { goTo("Create"); main.cvecara.value = fsi.cvecarainfo.value; main.count.value = fsi.cvecarainfo.value.Pictures.size; fsi.Watch()},
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(10.dp)
                    ) {
                        Text(text = "Izmeni podatke")
                        Spacer(modifier = Modifier.width(10.dp))
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit profile")
                    }
                    Button(
                        onClick = { main.DeleteFS(fsi.cvecarainfo.value, c) { goTo("Main")} }, modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Text(text = "Obrisi cvecaru")
                    }
                }
            }
        }
        Navbar(page = 0, goTo, main)
    }
}

@Composable
fun PictureDialog(Close: () -> Unit, picture: Uri){
    Dialog(onDismissRequest = { Close() }) {
        Column(modifier = Modifier
            .fillMaxSize()
            .clickable(onClick = Close), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Card(modifier = Modifier
                .fillMaxHeight(0.6f)
                .fillMaxWidth(0.8f)){
                AsyncImage(model = picture, contentDescription = "ShowPicture", modifier = Modifier.fillMaxSize())
                }
            }
    }
}
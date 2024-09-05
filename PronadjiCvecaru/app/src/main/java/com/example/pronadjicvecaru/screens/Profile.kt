package com.example.pronadjicvecaru.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.pronadjicvecaru.R
import com.example.pronadjicvecaru.ViewModels.MainVM
import com.example.pronadjicvecaru.ViewModels.SignUpVM
import kotlin.math.sign

@Composable
fun Profile(goTo: (String) -> Unit, signup: SignUpVM, main: MainVM){
    LaunchedEffect(key1 = 1) {
        signup.GetKorisnik()
    }
    var changedatadialog = remember { mutableStateOf(false)}
    Column {
        Column(modifier = Modifier.fillMaxHeight(0.92f)) {
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(
                    text = "O vama",
                    modifier = Modifier.padding(0.dp, 25.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 40.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .background(MaterialTheme.colorScheme.inverseSurface, RectangleShape)
            ) {}
            Row(modifier = Modifier
                .padding(10.dp, 70.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                ProfilePicture(signup = signup)
                Column(modifier = Modifier.padding(0.dp, 10.dp)) {
                    Text(
                        text = signup.korisnik.value.Name,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = signup.korisnik.value.Lastname,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Poeni:" + signup.korisnik.value.Points,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Button(onClick = { main.GetRangList(); goTo("RangList") }) {
                        Text(text = "Rang lista")
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(MaterialTheme.colorScheme.inverseSurface, RectangleShape)
            ) {}
            Row(
                modifier = Modifier
                    .padding(7.dp, 50.dp, 7.dp, 25.dp)
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                Text(text = "Username:", color = MaterialTheme.colorScheme.onBackground, fontSize = 20.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = signup.korisnik.value.Username, color = MaterialTheme.colorScheme.onBackground, fontSize = 20.sp)
            }
            Row(
                modifier = Modifier
                    .padding(7.dp, 25.dp)
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                Text(text = "Godine:", color = MaterialTheme.colorScheme.onBackground, fontSize = 20.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = signup.korisnik.value.YearsOld, color = MaterialTheme.colorScheme.onBackground, fontSize = 20.sp)
            }
            Row(
                modifier = Modifier
                    .padding(7.dp, 25.dp, 7.dp, 35.dp)
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                Text(text = "Email:", color = MaterialTheme.colorScheme.onBackground, fontSize = 20.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = signup.korisnik.value.Email, color = MaterialTheme.colorScheme.onBackground, fontSize = 20.sp)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { changedatadialog.value = true }, modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(10.dp)) {
                    Text(text = "Izmeni podatke")
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit profile")
                }
                Button(onClick = { signup.Logout { goTo("LogIn") } }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)) {
                    Text(text = "Odjavi se")
                }
            }
            if(changedatadialog.value)
                ChangeDataDialog (Close = {changedatadialog.value = false}, signup)
        }
        Navbar(page = 4, goTo, main)
    }
}

@Composable
fun ChangeDataDialog(Close: () -> Unit, signup: SignUpVM){
    signup.korisnikupdate.value = signup.korisnik.value
    Dialog(onDismissRequest = { Close() }) {
        Column(modifier = Modifier
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Card(modifier = Modifier
                .fillMaxHeight(0.6f)
                .fillMaxWidth(0.9f)){
                OutlinedTextField(value = signup.korisnikupdate.value.Name,
                    onValueChange = { signup.korisnikupdate.value = signup.korisnikupdate.value.copy(Name = it) },
                    label = {
                        Text(
                            text = "Ime"
                        )
                    },
                    modifier = Modifier.padding(24.dp, 20.dp)
                )
                OutlinedTextField(value = signup.korisnikupdate.value.Lastname,
                    onValueChange = { signup.korisnikupdate.value = signup.korisnikupdate.value.copy(Lastname = it) },
                    label = {
                        Text(
                            text = "Prezime"
                        )
                    },
                    modifier = Modifier.padding(24.dp, 20.dp)
                )
                OutlinedTextField(value = signup.korisnikupdate.value.Username,
                    onValueChange = { signup.korisnikupdate.value = signup.korisnikupdate.value.copy(Username = it) },
                    label = {
                        Text(
                            text = "Korisnicko ime"
                        )
                    },
                    modifier = Modifier.padding(24.dp, 30.dp, 24.dp, 20.dp)
                )
                OutlinedTextField(value = signup.korisnikupdate.value.YearsOld,
                    onValueChange = { signup.korisnikupdate.value = signup.korisnikupdate.value.copy(YearsOld = it) },
                    label = {
                        Text(
                            text = "Godine"
                        )
                    },
                    modifier = Modifier.padding(24.dp, 20.dp)
                )
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    Button(onClick = { Close() }, modifier = Modifier.width(100.dp)) {
                        Text(text = "Otkazi")
                    }
                    Button(onClick = { Close(); signup.UpdateKorisnikInfo(); }, modifier = Modifier.width(100.dp)) {
                        Text(text = "Potvrdi")
                    }
                }
            }
        }
    }
}

@Composable
fun ProfilePicture(signup: SignUpVM) {
        val c = LocalContext.current
        PPPicker(onImageSelected = { uri ->
            signup.CreatePicture(c, uri!!)
        }, signup)
}

@Composable
fun PPPicker(onImageSelected: (Uri?) -> Unit, signup: SignUpVM) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null)
            onImageSelected(uri)
    }
    Box(modifier = Modifier
        .width(100.dp)
        .height(100.dp)) {
        if (signup.picture.value != "")
        AsyncImage(
            model = signup.picture.value,
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = { launcher.launch("image/*") })
        )
        else{
            Icon(imageVector = Icons.Default.Person, contentDescription = "AddPicture", modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = { launcher.launch("image/*") }))
        }
    }
}
package com.example.pronadjicvecaru.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pronadjicvecaru.ViewModels.SignUpVM
import com.example.pronadjicvecaru.ui.theme.Blue1

@Composable
fun SignUp(goBack: () -> Unit, signup: SignUpVM, goTo: () -> Unit){
    Column(modifier = Modifier.fillMaxSize().background(Blue1), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){

        Card(shape = MaterialTheme.shapes.large, modifier = Modifier
            .fillMaxWidth(0.8f)
            .fillMaxHeight(0.8f)
            .verticalScroll(rememberScrollState()),
            colors = CardDefaults.cardColors(Color.White)){
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                    text = "Registracija", modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(0.dp, 10.dp), textAlign = TextAlign.Center, fontSize = 30.sp,
                    color = Color.Black
                )
                OutlinedTextField(value = signup.korisnikdata.value.Name,
                    onValueChange = { signup.korisnikdata.value = signup.korisnikdata.value.copy(Name = it) },
                    label = {
                        Text(
                            text = "Ime"
                        )
                    },
                    modifier = Modifier
                        .padding(24.dp, 10.dp, 24.dp, 5.dp)
                        .height(60.dp)
                )
                OutlinedTextField(value = signup.korisnikdata.value.Lastname,
                    onValueChange = { signup.korisnikdata.value = signup.korisnikdata.value.copy(Lastname = it) },
                    label = {
                        Text(
                            text = "Prezime"
                        )
                    },
                    modifier = Modifier
                        .padding(24.dp, 5.dp)
                        .height(60.dp)
                )
                OutlinedTextField(value = signup.korisnikdata.value.Username,
                    onValueChange = { signup.korisnikdata.value = signup.korisnikdata.value.copy(Username = it) },
                    label = {
                        Text(
                            text = "Korisnicko ime"
                        )
                    },
                    modifier = Modifier
                        .padding(24.dp, 5.dp)
                        .height(60.dp)
                )
                OutlinedTextField(value = signup.korisnikdata.value.Email,
                    onValueChange = { signup.korisnikdata.value = signup.korisnikdata.value.copy(Email = it) },
                    label = {
                        Text(
                            text = "E-mail"
                        )
                    },
                    modifier = Modifier
                        .padding(24.dp, 5.dp)
                        .height(60.dp)
                )
                OutlinedTextField(value = signup.password.value,
                    onValueChange = { signup.password.value = it },
                    label = {
                        Text(
                            text = "Lozinka"
                        )
                    },
                    modifier = Modifier
                        .padding(24.dp, 5.dp)
                        .height(60.dp),
                    visualTransformation = if (signup.passwordVissible1.value) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            signup.passwordVissible1.value = !signup.passwordVissible1.value
                        }) {
                            if(!signup.passwordVissible1.value)
                                Icon(imageVector = Icons.Filled.Info, "Show")
                            else
                                Icon(imageVector = Icons.Filled.Close, "Hide")
                        }
                    },
                    singleLine = true
                )
                OutlinedTextField(value = signup.passwordConfirm.value,
                    onValueChange = { signup.passwordConfirm.value = it },
                    label = {
                        Text(
                            text = "Potvrdi lozinku"
                        )
                    },

                    placeholder = { if(signup.passwordFail.value){
                        Text(
                            text = "Lozinke se ne poklapaju"
                        )
                    }},
                    modifier = Modifier
                        .padding(24.dp, 5.dp)
                        .height(60.dp),
                    visualTransformation = if (signup.passwordVissible2.value) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            signup.passwordVissible2.value = !signup.passwordVissible2.value
                        }) {
                            if(!signup.passwordVissible2.value)
                                Icon(imageVector = Icons.Filled.Info, "Show")
                            else
                                Icon(imageVector = Icons.Filled.Close, "Hide")
                        }
                    },
                    singleLine = true,
                    isError = signup.passwordFail.value
                )
                OutlinedTextField(value = signup.korisnikdata.value.YearsOld,
                    onValueChange = { signup.korisnikdata.value = signup.korisnikdata.value.copy(YearsOld = it) },
                    label = {
                        Text(
                            text = "Broj godina"
                        )
                    },
                    modifier = Modifier
                        .padding(24.dp, 5.dp)
                        .height(60.dp)
                )
                Row(modifier = Modifier.fillMaxWidth().padding(0.dp,20.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    val c = LocalContext.current
                    Button(onClick = { signup.passCheck(goTo) }){
                        Text(text = "Potvrdi")
                    }
                    Button(onClick = { goBack() }) {
                        Text(text = "Otkazi")
                    }
                }
            }
        }
    }
}
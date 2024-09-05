package com.example.pronadjicvecaru.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import com.example.pronadjicvecaru.ViewModels.LogInVM
import com.example.pronadjicvecaru.ui.theme.Blue1

@Composable
fun LogIn(goTo: (String) -> Unit, login: LogInVM){
    Column(modifier = Modifier.fillMaxSize().background(Blue1), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){

        Card(shape = MaterialTheme.shapes.large, modifier = Modifier
            .fillMaxWidth(0.7f).fillMaxHeight(0.5f),
            colors = CardDefaults.cardColors(Color.White)
        ){
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                    text = "Prijavljivanje", modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(0.dp, 10.dp), textAlign = TextAlign.Center, fontSize = 40.sp,
                    color = Color.Black
                )
                OutlinedTextField(value = login.email.value,
                    onValueChange = { login.email.value = it },
                    label = {
                        Text(
                            text = "E-mail"
                        )
                    },
                    modifier = Modifier.padding(24.dp, 30.dp)
                )
                OutlinedTextField(value = login.pass.value,
                    onValueChange = { login.pass.value = it },
                    label = {
                        Text(
                            text = "Lozinka"
                        )
                    },
                    modifier = Modifier.padding(24.dp, 0.dp, 24.dp, 30.dp),
                    visualTransformation = if (login.passwordVissible.value) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                        trailingIcon = {
                            IconButton(onClick = {
                                login.passwordVissible.value = !login.passwordVissible.value
                            }) {
                                if(!login.passwordVissible.value)
                                    Icon(imageVector = Icons.Filled.Info, "Show")
                                else
                                    Icon(imageVector = Icons.Filled.Close, "Hide")
                            }
                    },
                    singleLine = true
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val c = LocalContext.current
                    Button(onClick = { login.Signin() }) {
                        Text(text = "Prijavi se")
                    }
                    Button(onClick = { goTo("SignUp") }) {
                        Text(text = "Kreiraj nalog")
                    }
                }
            }
        }
    }
}

package com.example.pronadjicvecaru.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pronadjicvecaru.R

@Composable
fun Start(goTo : (String) -> Unit){
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .paint(
                    painterResource(id = R.drawable.main),
                    contentScale = ContentScale.FillBounds
                )
        )
        Row(modifier = Modifier
            .padding(20.dp)
            .align(Alignment.CenterHorizontally)) {
            Button(onClick = { goTo("LogIn") }, modifier = Modifier.width(300.dp)) {
                Text(text = "Prijavi se")
            }
        }
        Row(modifier = Modifier
            .padding(15.dp)
            .align(Alignment.CenterHorizontally)) {
            Button(onClick = { goTo("SignUp") }, modifier = Modifier.width(300.dp)) {
                Text(text = "Registruj se")
            }
        }
    }
}
package com.example.pronadjicvecaru.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pronadjicvecaru.ViewModels.MainVM

@Composable
fun RangList(main: MainVM, back: () -> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
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
            Text(text = "Rang lista", fontSize = 20.sp, color = Color.White)
            Text(text = "          ")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MaterialTheme.colorScheme.inverseSurface, RectangleShape)
        ) {}
        for(r in main.ranglist.value){
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp, 10.dp), 
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = r.Username, fontSize = 20.sp)
                Text(text = "Points: " + r.Points, fontSize = 20.sp)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.inverseSurface, RectangleShape)
            ) {}
        }
    }
}
package com.example.pronadjicvecaru.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pronadjicvecaru.ViewModels.FlowerShopInfoVM

@Composable
fun Reviews(back: () -> Unit, fsi: FlowerShopInfoVM) {

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
            Text(text = "Recenzije", fontSize = 20.sp, color = Color.White)
            Text(text = "          ")
        }
        Column(modifier = Modifier
            .fillMaxHeight(0.3f)
            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text(text = "Ostavite ocenu")

            Spacer(modifier = Modifier.height(16.dp))

            RatingBar(fsi.rating.value) { newRating ->
                fsi.rating.value = newRating
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = fsi.comment.value,
                onValueChange = { fsi.comment.value = it },
                label = { Text("Unesite komentar") },
                modifier = Modifier.fillMaxWidth(0.7f),
                enabled = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            val c = LocalContext.current
            Button(onClick = { fsi.CreateReview(c) }) {
                Text("Potvrdi")
            }
        }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(MaterialTheme.colorScheme.inverseSurface, RectangleShape)
            ) {}
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())) {
            for (r in fsi.recenzije.value) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Username: " + r.Username)
                        Row {
                            Text(text = r.NumberOfStars.toString())
                            Icon(imageVector = Icons.Default.Star, contentDescription = "")
                        }
                    }
                    Text(r.Comment)
                }
            }
        }
    }
}

@Composable
fun RatingBar(rating: Double, onRatingChanged: (Double) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..5) {
            Icon(
                imageVector = if (i <= rating) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(4.dp)
                    .clickable { onRatingChanged(i.toDouble()) }
            )
        }
    }
}
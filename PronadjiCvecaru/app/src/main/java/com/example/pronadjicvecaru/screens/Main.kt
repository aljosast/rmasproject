package com.example.pronadjicvecaru.screens

import android.annotation.SuppressLint
import android.graphics.drawable.shapes.Shape
import android.util.Log
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.pronadjicvecaru.R
import com.example.pronadjicvecaru.ViewModels.FlowerShopInfoVM
import com.example.pronadjicvecaru.ViewModels.MainVM
import com.example.pronadjicvecaru.data.CvecaraData
import com.example.pronadjicvecaru.data.Location
import com.example.pronadjicvecaru.ui.theme.Blue1

@SuppressLint("UnrememberedMutableState")
@Composable
fun Main(main: MainVM, goTo: (String) -> Unit, fsi: FlowerShopInfoVM) {
    Column() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .paint(
                    painterResource(id = R.drawable.main1),
                    contentScale = ContentScale.FillBounds
                )
        )
    }
    if (main.page.value) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column(modifier = Modifier.fillMaxHeight(0.92f)) {
                Spacer(modifier = Modifier.height(60.dp))
                Card(
                    shape = MaterialTheme.shapes.large, modifier = Modifier
                        .fillMaxWidth()
                        .padding(60.dp, 130.dp, 60.dp, 0.dp),
                    border = BorderStroke(1.dp, Color.Black)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "Pronadjite najbolju cvecaru u vasoj okolini",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(0.dp, 5.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color.Black, RectangleShape)
                        ) {}
                        Row(
                            modifier = Modifier
                                .padding(16.dp, 5.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(text = "Dostava:", color = MaterialTheme.colorScheme.onBackground)
                            Spacer(modifier = Modifier.width(15.dp))
                            Checkbox(
                                checked = main.dostava.value,
                                onCheckedChange = { main.dostava.value = it }
                            )
                        }
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                Modifier
                                    .fillMaxWidth(0.5f)
                                    .padding(0.dp, 20.dp), horizontalAlignment = Alignment.End
                            ) {
                                Text(text = "U krugu(km):", color = MaterialTheme.colorScheme.onBackground)
                            }
                            TextField(
                                value = main.radius.value,
                                onValueChange = { main.radius.value = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp, 5.dp, 40.dp, 0.dp),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                colors = OutlinedTextFieldDefaults.colors()
                            )
                        }
                        Row(
                            modifier = Modifier
                                .padding(16.dp, 5.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = "Minimalna ocena:", color = MaterialTheme.colorScheme.onBackground)
                            Spacer(modifier = Modifier.width(8.dp))
                            IconButton(
                                onClick = {
                                    if (main.ocena.value > 0) main.ocena.value -= 0.5
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowLeft,
                                    contentDescription = "Decrement",
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                            }
                            Text(
                                text = main.ocena.value.toString(),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(horizontal = 0.dp),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            IconButton(
                                onClick = {
                                    if (main.ocena.value < 5) main.ocena.value += 0.5
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowRight,
                                    contentDescription = "Increment",
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(
                                onClick = { main.page.value = false; main.filter() }, modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = RectangleShape
                                    )
                            ) {
                                Text(text = "Pretrazi")
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.fillMaxHeight(0.5f))
            }
            Navbar(1, goTo, main)
        }
    }
    else {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxHeight(0.92f)) {
                Spacer(modifier = Modifier.fillMaxHeight(0.45f))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp, 10.dp)
                ) {
                    Button(
                        onClick = { main.page.value = true }, modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RectangleShape
                            )
                    ) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                        Text(text = "Pronadjite najbolju cvecaru u vasoj okolini")
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState())
                ) {
                    for(c in main.cvecarefiltered.value){
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)) {
                            Button(
                                onClick = { goTo("FlowerShopInfo"); fsi.cvecarainfo.value = c; fsi.id.value = main.GetID(); fsi.GetReviews(); fsi.GetRecomendations()},
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(0.dp, 5.dp), colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                            ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight(0.5f)
                                            .horizontalScroll(rememberScrollState()),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(text = c.Name, fontSize = 15.sp)
                                        Text(text = "Kontakt: " + c.Number, fontSize = 15.sp)
                                        Row(
                                        ) {
                                            Text(text = c.Ocena.toString(), fontSize = 16.sp)
                                            Icon(
                                                imageVector = Icons.Default.Star,
                                                contentDescription = "Ocena"
                                            )
                                        }
                                    }
                            }
                        }
                    }
                }
            }
            Navbar(1, goTo, main)
        }
    }
}

@Composable
fun Navbar(page: Int, goTo: (String) -> Unit, main: MainVM){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(MaterialTheme.colorScheme.inverseSurface, RectangleShape)
    ) {}
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)
            .padding(0.dp, 10.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Icon(
            imageVector = Icons.Default.Home,
            contentDescription = "Home",
            modifier = Modifier
                .padding(8.dp)
                .clickable(onClick = { goTo("Main") }),
            tint = if(page == 1)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onBackground
        )
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Create",
            modifier = Modifier
                .padding(8.dp)
                .clickable(onClick = { goTo("Create") ; main.cvecara.value = CvecaraData(Lat = Location.Getx(), Lng = Location.Gety(),
                    RadnoVreme = "Radnim danima: 8-17\nVikendom: 9-15") ; main.count.value = 0}),
            tint = if(page == 2)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onBackground
        )
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Location",
            modifier = Modifier
                .padding(8.dp)
                .clickable(onClick = { goTo("MapsFilter") }),
            tint = if(page == 3)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onBackground
        )
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Profile",
            modifier = Modifier
                .padding(8.dp)
                .clickable(onClick = { goTo("Profile") }),
            tint = if(page == 4)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onBackground
        )
    }
}
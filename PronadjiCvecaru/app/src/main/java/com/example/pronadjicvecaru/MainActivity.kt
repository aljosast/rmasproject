package com.example.pronadjicvecaru

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pronadjicvecaru.ViewModels.FlowerShopInfoFactory
import com.example.pronadjicvecaru.ViewModels.FlowerShopInfoVM
import com.example.pronadjicvecaru.ViewModels.LogInFactory
import com.example.pronadjicvecaru.ViewModels.LogInVM
import com.example.pronadjicvecaru.ViewModels.MainVM
import com.example.pronadjicvecaru.ViewModels.MainVMFactory
import com.example.pronadjicvecaru.ViewModels.SignUpFactory
import com.example.pronadjicvecaru.ViewModels.SignUpVM
import com.example.pronadjicvecaru.repository.Authentication
import com.example.pronadjicvecaru.screens.Create
import com.example.pronadjicvecaru.screens.FlowerShopInfo
import com.example.pronadjicvecaru.screens.LogIn
import com.example.pronadjicvecaru.screens.Main
import com.example.pronadjicvecaru.screens.Maps
import com.example.pronadjicvecaru.screens.MapsFilter
import com.example.pronadjicvecaru.screens.Profile
import com.example.pronadjicvecaru.screens.RangList
import com.example.pronadjicvecaru.screens.Reviews
import com.example.pronadjicvecaru.screens.SignUp
import com.example.pronadjicvecaru.screens.Start
import com.example.pronadjicvecaru.screens.UserRecomendation
import com.example.pronadjicvecaru.services.LocationTrackerService
import com.example.pronadjicvecaru.ui.theme.PronadjiCvecaruTheme

class MainActivity : ComponentActivity() {
    private var i: Intent? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            0
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            requestBGLocationPermission()
        }
        setContent {
            PronadjiCvecaruTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavApp()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        i = Intent(this, LocationTrackerService::class.java)
        startService(i)
    }

    override fun onStop() {
        super.onStop()
        stopService(i)
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    fun requestBGLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ),
            0
        )
    }
}

@Composable
fun NavApp() {

    val navController = rememberNavController()

    val loginvm : LogInVM = viewModel(factory = LogInFactory(go = {navController.navigate(Screens.Main.name)}))
    val signupvm : SignUpVM = viewModel(factory = SignUpFactory())
    val mainvm : MainVM = viewModel(factory = MainVMFactory())
    val flowershopinfovm : FlowerShopInfoVM = viewModel(factory = FlowerShopInfoFactory())
    val screen : Screens
    val auth = Authentication()
    auth.Init()
    if(auth.isLoggedIn()) screen = Screens.Main
    else screen = Screens.Start

    NavHost(navController = navController, startDestination = screen.name) {
        composable(Screens.Start.name) {
            Start(goTo = navController::navigate)
        }
        composable(Screens.LogIn.name) {
            LogIn(goTo= navController::navigate, login = loginvm)
        }
        composable(Screens.SignUp.name) {
            SignUp(goBack = { navController.popBackStack() }, signup = signupvm, goTo = {navController.navigate(Screens.LogIn.name)})
        }
        composable(Screens.Main.name) {
            Main(main = mainvm, goTo = navController::navigate, fsi = flowershopinfovm)
        }
        composable(Screens.Create.name) {
            Create(goTo = navController::navigate, main = mainvm)
        }
        composable(Screens.Profile.name) {
            if(auth.isLoggedIn())
                Profile(goTo = navController::navigate, signup = signupvm, main = mainvm)
            else
                navController.navigate(Screens.LogIn.name)
        }
        composable(Screens.FlowerShopInfo.name) {
            FlowerShopInfo(goTo = navController::navigate, fsi = flowershopinfovm, main = mainvm)
        }
        composable(Screens.Reviews.name) {
            Reviews(back = { navController.popBackStack() }, fsi = flowershopinfovm)
        }
        composable(Screens.Maps.name) {
            Maps(mainvm.cvecara.value, true)
        }
        composable(Screens.Maps1.name) {
            Maps(flowershopinfovm.cvecarainfo.value, false)
        }
        composable(Screens.MapsFilter.name) {
            MapsFilter(main = mainvm, goTo = navController::navigate)
        }
        composable(Screens.UserRecomendation.name) {
            UserRecomendation(back = { navController.popBackStack() }, fsi = flowershopinfovm)
        }
        composable(Screens.RangList.name) {
            RangList(main = mainvm,back = { navController.popBackStack() })
        }
    }
}

enum class Screens {
    Start,
    LogIn,
    SignUp,
    Main,
    Create,
    Profile,
    FlowerShopInfo,
    Reviews,
    Maps,
    Maps1,
    MapsFilter,
    UserRecomendation,
    RangList
}
package com.example.pronadjicvecaru.ViewModels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pronadjicvecaru.data.CvecaraData
import com.example.pronadjicvecaru.data.RecenzijaData
import com.example.pronadjicvecaru.data.UserRecomendationData
import com.example.pronadjicvecaru.repository.Authentication
import com.example.pronadjicvecaru.repository.Data
import com.example.pronadjicvecaru.repository.Storage

class FlowerShopInfoVM(private val repository: Data, private val repository1: Storage): ViewModel() {
    var cvecarainfo = mutableStateOf(CvecaraData())
    var id = mutableStateOf("")
    var rating = mutableStateOf(0.0)
    var comment = mutableStateOf("")
    var recenzije = mutableStateOf(emptyList<RecenzijaData>())
    var recomendation = mutableStateOf("")
    var userpicture = mutableStateOf("")
    var recomendations = mutableStateOf(emptyList<UserRecomendationData>())

    fun Watch(){
        for (p in cvecarainfo.value.Pictures){
            Log.d("FSIIMage",p.toString())
        }
    }

    fun CreateReview(context: Context){
        repository.CreateReview(RecenzijaData("", comment.value, rating.value, cvecarainfo.value.UserID + cvecarainfo.value.Adresa),
            cvecarainfo.value, context)
        {
                list -> recenzije.value = list
        }
    }

    fun GetReviews(){
        repository.GetReviews(cvecarainfo.value.UserID + cvecarainfo.value.Adresa){
            list -> recenzije.value = list
        }
    }

    fun CreateRecomendation(context: Context){
        repository.CreateRecomendation(
            UserRecomendationData("", cvecarainfo.value.UserID + cvecarainfo.value.Adresa, "", recomendation.value), context)
        {
            list -> recomendations.value = list
            repository1.GetUserURL(context, Uri.parse(userpicture.value), cvecarainfo.value.UserID + cvecarainfo.value.Adresa)
        }
    }

    fun GetRecomendations(){
        repository.GetRecomendations(cvecarainfo.value.UserID + cvecarainfo.value.Adresa)
        {
            list -> recomendations.value = list
        }
    }

    init {
        repository.Init()
        repository1.Init()
    }
}

class FlowerShopInfoFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlowerShopInfoVM::class.java)) {
            return FlowerShopInfoVM(Data(), Storage()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
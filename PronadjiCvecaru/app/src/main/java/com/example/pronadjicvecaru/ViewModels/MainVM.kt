package com.example.pronadjicvecaru.ViewModels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pronadjicvecaru.data.CvecaraData
import com.example.pronadjicvecaru.data.KorisnikData
import com.example.pronadjicvecaru.data.Location
import com.example.pronadjicvecaru.repository.Authentication
import com.example.pronadjicvecaru.repository.Data
import com.example.pronadjicvecaru.repository.Storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.pow
import kotlin.math.sqrt

class MainVM(private val repository: Data, private val repository1: Storage): ViewModel() {

    var dostava = mutableStateOf(false)
    var ocena = mutableStateOf(0.0)
    var radius = mutableStateOf("")
    var page = mutableStateOf(true)
    var cvecara = mutableStateOf(CvecaraData())
    var cvecare = mutableStateOf(emptyList<CvecaraData>())
    var cvecarefiltered = mutableStateOf(emptyList<CvecaraData>())
    var checkProfilePicture = mutableStateOf(false)
    var count = mutableStateOf(cvecara.value.Pictures.size)
    var ranglist = mutableStateOf(emptyList<KorisnikData>())

    fun GetID(): String{
        return repository.GetId()
    }

    fun filter(){
        cvecarefiltered.value = emptyList()
        for(c in cvecare.value)
            if(radius.value == "")
            {
                if (c.Isporuka == dostava.value && c.Ocena >= ocena.value)
                {
                    cvecarefiltered.value += c
                }
            }
            else
            {
                if(c.Isporuka == dostava.value && c.Ocena >= ocena.value &&
                    sqrt((c.Lat - Location.Getx()).pow(2) + (c.Lng - Location.Gety()).pow(2)) * 111 <= radius.value.toInt())
                {
                    cvecarefiltered.value += c
                }
            }
            cvecarefiltered.value = cvecarefiltered.value.sortedByDescending { it.Ocena }
        Log.d("Filter","Ending")
    }

    fun CreateFS(context: Context){
        var list = cvecara.value.Pictures
        repository.CreateFlowerShop(cvecara.value, context = context)
        if(cvecara.value.ProfilePicture != "" && checkProfilePicture.value)
            CreatePicture(context, Uri.parse(cvecara.value.ProfilePicture))

        CoroutineScope(Dispatchers.IO).launch {
            if(count.value < list.size)
                for (p in count.value..list.size - 1) {
                    Log.d("adding pic", "start")
                    CreatePictures(context, Uri.parse(list[p]), p)
                }
        }
    }

    fun CreatePicture(context: Context, pic: Uri){

        repository1.GetFlowerShopURL(context, pic, cvecara.value.Adresa){
            url -> cvecara.value.ProfilePicture = url
        }
    }

    fun CreatePictures(context: Context, pic: Uri, i: Int){
        Log.d("adding pic", "entering function")
        repository1.GetFlowerShopURLs(context, pic, cvecara.value.Adresa, i){
            url -> val list = cvecara.value.Pictures.toTypedArray()
            Log.d("Slika $i",url)
            list[i] = url
            cvecara.value.Pictures = list.toList()
            GetAllFS()
        }
    }

    fun GetAllFS(){
        repository.GetAllFlowerShops{
            list -> cvecare.value = list
            for (c in cvecare.value)
                Log.d("c",c.Name)

            Log.d("Filter","Starting")
            filter()
        }
    }

    fun DeleteFS(cvecaraData: CvecaraData, context: Context, goto: () -> Unit){
        repository.DeleteFlowerShop(cvecaraData, context) { GetAllFS(); goto() }
    }

    fun GetRangList(){
        repository.GetAllUsers{
            list -> ranglist.value = list
            ranglist.value = ranglist.value.sortedByDescending { it.Points }
        }
    }

    init {
        repository.Init()
        repository1.Init()
        GetAllFS()
    }
}
class MainVMFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainVM::class.java)) {
            return MainVM(Data(), Storage()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
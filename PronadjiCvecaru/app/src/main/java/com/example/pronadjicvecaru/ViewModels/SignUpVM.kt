package com.example.pronadjicvecaru.ViewModels

import android.content.Context
import android.graphics.Picture
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pronadjicvecaru.data.KorisnikData
import com.example.pronadjicvecaru.repository.Authentication
import com.example.pronadjicvecaru.repository.Storage

class SignUpVM(private val repository: Authentication, private val repository1: Storage): ViewModel() {

    var korisnikdata = mutableStateOf(KorisnikData())
    var password = mutableStateOf("")
    var passwordConfirm = mutableStateOf("")
    var passwordVissible1 = mutableStateOf(false)
    var passwordVissible2 = mutableStateOf(false)
    var passwordFail = mutableStateOf(false)
    var picture = mutableStateOf("")
    var korisnik = mutableStateOf(KorisnikData())
    var korisnikupdate = mutableStateOf(KorisnikData())

    fun passCheck(goto: () -> Unit) {
        if(password.value != passwordConfirm.value) {
            passwordFail.value = true
            passwordConfirm.value = ""
        }
        else{
            passwordFail.value = false
            Registration(goto)
        }
    }

    fun GetKorisnik(){
        repository.GetUserData { k -> korisnik.value = k
            picture.value = k.Picture
        }
    }

    fun UpdateKorisnikInfo(){
        repository.UpdateUser(korisnikupdate.value)
        korisnik.value = korisnikupdate.value
    }

    fun Registration(goto: () -> Unit){
        repository.SignUp(korisnikdata.value.Name, korisnikdata.value.Lastname, korisnikdata.value.Email, korisnikdata.value.Username,
            password.value, korisnikdata.value.YearsOld, goto)
    }

    fun Logout(go: () -> Unit){
        repository.LogOut(go)
    }

    fun CreatePicture(context: Context, pic: Uri){
        repository1.GetURL(context, pic){
            url -> korisnik.value.Picture = url
            picture.value = url
        }
    }

    init{
        repository.Init()
        repository1.Init()
    }
}

class SignUpFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpVM::class.java)) {
            return SignUpVM(Authentication(), Storage()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package com.example.pronadjicvecaru.repository

import android.util.Log
import com.example.pronadjicvecaru.data.KorisnikData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class Authentication {
    private lateinit var auth: FirebaseAuth
    private lateinit var store: FirebaseFirestore

    fun Init(){
        Log.d("init", "uspesan init")
        auth = Firebase.auth
        store = Firebase.firestore
    }
    fun SignUp(name: String, lastname: String, email: String, username: String, pass: String, yearsold: String, goto: () -> Unit) {
        try {
            Log.d("signup", "korisnik kreiran $email $pass")
            auth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener {
                val korisnik = KorisnikData(name, lastname, username, email, yearsold)
                store.collection("KorisnikData").document(auth.currentUser!!.uid).set(korisnik)
                    .addOnSuccessListener {
                        Log.d("signup", "korisnik kreiran")
                    }.addOnFailureListener {
                    Log.d("signup", "korisnik bezuspesno kreiran")
                }
                val update = userProfileChangeRequest {
                    displayName = username
                }
                auth.currentUser!!.updateProfile(update)
                goto()
            }.addOnFailureListener {
                Log.d("signup", "korisnik bezuspesno kreiran")
            }
        }
        catch (ex: Exception){
            Log.d("catch", ex.message.toString())
        }
    }

    fun SignIn(email: String, pass: String, go: () -> Unit) {
        try {
            Log.d("signin", "korisnik $email $pass")
            auth.signInWithEmailAndPassword(email, pass).addOnSuccessListener {
                Log.d("signin", "korisnik uspesno prijvaljen")
                go()
            }.addOnFailureListener {
                Log.d("signin", "korisnik bezuspesno prijavljen")
            }
        }
        catch (ex: Exception){
            Log.d("catch", ex.message.toString())
        }
    }

    fun GetUserData(set: (KorisnikData) -> Unit){
        try{
            var korisnik = KorisnikData()
            store.collection("KorisnikData").document(auth.currentUser!!.uid).get()
                .addOnSuccessListener {
                    korisnik = it.toObject<KorisnikData>()!!
                    set(korisnik)
                    Log.d("getuser", "korisnik preuzet")
                }.addOnFailureListener {
                    Log.d("getuser", "korisnik bezuspesno preuzet")
                }
        }
        catch (ex: Exception){
            Log.d("catch", ex.message.toString())
        }
    }

    fun LogOut(go: () -> Unit){
        try{
            auth.signOut()
            go()
        }
        catch (ex: Exception){
            Log.d("catch", ex.message.toString())
        }
    }

    fun UpdateUser(user: KorisnikData){
        try {
            store.collection("KorisnikData").document(auth.currentUser!!.uid).set(user).addOnSuccessListener {
                val update = userProfileChangeRequest {
                    displayName = user.Username
                }
                auth.currentUser!!.updateProfile(update)
            }
        }
        catch (ex: Exception){
            Log.d("catch", ex.message.toString())
        }
    }

    fun isLoggedIn() : Boolean{
        return auth.currentUser != null
    }
}
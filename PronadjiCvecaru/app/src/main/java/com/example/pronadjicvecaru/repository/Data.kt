package com.example.pronadjicvecaru.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.pronadjicvecaru.data.CvecaraData
import com.example.pronadjicvecaru.data.KorisnikData
import com.example.pronadjicvecaru.data.RecenzijaData
import com.example.pronadjicvecaru.data.UserRecomendationData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects

class Data {
    private lateinit var auth: FirebaseAuth
    private lateinit var store: FirebaseFirestore

    fun Init(){
        Log.d("init", "uspesan init")
        auth = Firebase.auth
        store = Firebase.firestore
    }

    fun GetId(): String{
        return auth.currentUser!!.uid
    }

    fun CreateFlowerShop(cvecara: CvecaraData, context: Context){
        try {
            cvecara.UserID = auth.currentUser!!.uid
            store.collection("ListaCvecara").document(auth.currentUser!!.uid + cvecara.Adresa)
                .set(cvecara).addOnSuccessListener {
                    Toast.makeText(context, "Uspeno ste kreirali cvecaru", Toast.LENGTH_SHORT).show()
                    Log.d("kreiranje", "uspesno kreirana cvecara")
                }
                .addOnFailureListener {
                    Log.d("kreiranje", "neuspesno kreirana cvecara")
                }
        }
        catch (ex: Exception){
            Log.d("catch", ex.message.toString())
        }
    }
    fun GetAllFlowerShops(set: (List<CvecaraData>) -> Unit){
        try{
            store.collection("ListaCvecara").get().addOnSuccessListener {
                set(it.toObjects<CvecaraData>())
            }
                .addOnFailureListener{
                    Log.d("preuzimanje", "neuspesno")
                }
        }
        catch (ex: Exception){
            Log.d("catch", ex.message.toString())
        }
    }
    fun DeleteFlowerShop(cvecara: CvecaraData, context: Context, goto: () -> Unit){
        try{
            store.collection("ListaCvecara").document(auth.currentUser!!.uid+cvecara.Adresa).delete()
                .addOnSuccessListener {
                    Log.d("brisanje", "uspesno")
                    Toast.makeText(context, "Uspeno ste obrisali cvecaru", Toast.LENGTH_SHORT).show()
                    goto()
                }
                .addOnFailureListener{
                    Log.d("brisanje", "neuspesno")
                }
        }
        catch (ex: Exception){
            Log.d("catch", ex.message.toString())
        }
    }
    fun CreateReview(review: RecenzijaData, fs: CvecaraData, context: Context, callback: (List<RecenzijaData>) -> Unit){
        try{
            review.Username = auth.currentUser!!.displayName!!
            store.collection("ListRecenzija").document(auth.currentUser!!.uid + review.FlowerShopID).set(review)
                .addOnSuccessListener {
                    Toast.makeText(context, "Uspeno ste dodali komentar", Toast.LENGTH_SHORT).show()
                    GetReviews(fs.UserID+fs.Adresa){
                        list -> fs.Ocena = (fs.Ocena * (list.size - 1) + review.NumberOfStars)/list.size
                        try {
                            store.collection("ListaCvecara").document(fs.UserID+fs.Adresa)
                                .set(fs).addOnSuccessListener {
                                    Log.d("kreiranje", "uspesno kreirana cvecara")
                                    AddPoints(5, context)
                                }
                                .addOnFailureListener {
                                    Log.d("kreiranje", "neuspesno kreirana cvecara")
                                }
                        }
                        catch (ex: Exception){
                            Log.d("catch", ex.message.toString())
                        }
                        callback(list)
                    }
                }
                .addOnFailureListener{
                    Log.d("review", "Neuspesno dodata recenzija")
                }
        }
        catch (ex: Exception){
            Log.d("catch", ex.message.toString())
        }
    }
    fun GetReviews(id: String, callback: (List<RecenzijaData>) -> Unit){
        try{
            store.collection("ListRecenzija").whereEqualTo("flowerShopID", id).get()
                .addOnSuccessListener {
                    callback(it.toObjects<RecenzijaData>())
                }
                .addOnFailureListener{
                    Log.d("review", "Neuspesno preuzete recenzije")
                }
        }
        catch (ex: Exception){
            Log.d("catch", ex.message.toString())
        }
    }
    fun CreateRecomendation(recomendation: UserRecomendationData, context: Context, callback: (List<UserRecomendationData>) -> Unit){
        try{
            recomendation.Username = auth.currentUser!!.displayName!!
            store.collection("Predlozi").document(recomendation.FlowerShopID + auth.currentUser!!.uid).set(recomendation)
                .addOnSuccessListener {
                    Toast.makeText(context, "Uspeno ste dodali predlog", Toast.LENGTH_SHORT).show()
                    GetRecomendations(recomendation.FlowerShopID){
                        list -> callback(list)
                    }
                    AddPoints(10, context)
                }
                .addOnFailureListener{
                    Log.d("review", "Neuspesno dodat predlog")
                }
        }
        catch (ex: Exception){
            Log.d("catch", ex.message.toString())
        }
    }
    fun GetRecomendations(id: String, callback: (List<UserRecomendationData>) -> Unit){
        try{
            store.collection("Predlozi").whereEqualTo("flowerShopID", id).get()
                .addOnSuccessListener {
                    callback(it.toObjects<UserRecomendationData>())
                }
                .addOnFailureListener{
                    Log.d("review", "Neuspesno preuzeti predlozi")
                }
        }
        catch (ex: Exception){
            Log.d("catch", ex.message.toString())
        }
    }
    fun GetAllUsers(set: (List<KorisnikData>) -> Unit){
        try {
            store.collection("KorisnikData").get()
                .addOnSuccessListener {
                    set(it.toObjects<KorisnikData>())
                    Log.d("get users", "uspesno ste preuzeli rang listu")
                }
                .addOnFailureListener{
                    Log.d("get users", "neuspesno ste preuzeli rang listu")
                }
        }
        catch (ex: Exception){
            Log.d("catch", ex.message.toString())
        }
    }
    fun AddPoints(points: Int, context: Context){
        try {
            store.collection("KorisnikData").document(auth.currentUser!!.uid).get().addOnSuccessListener {
                var user = it.toObject<KorisnikData>()
                user!!.Points += points
                store.collection("KorisnikData").document(auth.currentUser!!.uid).set(user!!).addOnSuccessListener {
                    Toast.makeText(context, "Dobili ste ${points} poena", Toast.LENGTH_SHORT).show()
                }
            }
        }
        catch (ex: Exception){
            Log.d("catch", ex.message.toString())
        }
    }
}
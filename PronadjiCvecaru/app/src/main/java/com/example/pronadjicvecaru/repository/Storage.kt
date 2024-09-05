package com.example.pronadjicvecaru.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.example.pronadjicvecaru.data.CvecaraData
import com.example.pronadjicvecaru.data.KorisnikData
import com.example.pronadjicvecaru.data.UserRecomendationData
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import java.io.ByteArrayOutputStream
import java.io.InputStream

class Storage {
    private lateinit var auth: FirebaseAuth
    private lateinit var store: FirebaseFirestore
    private lateinit var storage: FirebaseStorage

    fun Init(){
        Log.d("init", "uspesan init")
        auth = Firebase.auth
        store = Firebase.firestore
        storage = Firebase.storage
    }

    private fun UploadPicture(picture: Bitmap?): Task<Uri> {
        val imageRef: StorageReference = storage.reference.child("AccountPicture")
            .child(auth.currentUser!!.uid).child(auth.currentUser!!.uid + ".jpg")
        val baos = ByteArrayOutputStream()
        val bitmap = picture
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = imageRef.putBytes(data)
        val urlTask = uploadTask.continueWithTask{
            task -> if(!task.isSuccessful){
                task.exception?.let {
                    Log.d("upload", it.message.toString())
                }
            }
            imageRef.downloadUrl
        }
        return urlTask
    }

    fun GetURL(context: Context, picture: Uri, putPlace: (String) -> Unit){
        var pic:Bitmap?
        val contentResolver = context.contentResolver
        val inputStream: InputStream? = contentResolver.openInputStream(picture)

        if(inputStream == null){
            Log.d("geturl", "Inputstream nije otvoren")
            return
        }

        pic = BitmapFactory.decodeStream(inputStream)
        if(pic == null) {
            Log.d("geturl", "Slika nije dekodirana")
            return
        }

        UploadPicture(pic).addOnCompleteListener { task ->
            if (task.isSuccessful){
                val imageUrl = task.result.toString()
                putPlace(imageUrl)
                StorePicture(imageUrl)
            }
        }
    }

    fun StorePicture(url: String){
        store.collection("KorisnikData").document(auth.currentUser!!.uid).get().addOnSuccessListener {
            var client = it.toObject<KorisnikData>()
            client!!.Picture = url
            store.collection("KorisnikData").document(auth.currentUser!!.uid).set(client).addOnSuccessListener {
                Log.d("storepicture", "slika uspesno uploadovana")
            }
                .addOnFailureListener{
                    Log.d("storepicture", "slika neuspesno uploadovana")
                }
        }
            .addOnFailureListener{
            Log.d("storepicture", "slika neuspesno uploadovana")
        }
    }

    private fun UploadFlowerShopPicture(picture: Bitmap?, adress: String): Task<Uri> {
        val imageRef: StorageReference = storage.reference.child("FlowerShopPicture")
            .child(auth.currentUser!!.uid + adress).child("profilepicture.jpg")
        val baos = ByteArrayOutputStream()
        val bitmap = picture
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = imageRef.putBytes(data)
        val urlTask = uploadTask.continueWithTask{
                task -> if(!task.isSuccessful){
            task.exception?.let {
                Log.d("upload", it.message.toString())
            }
        }
            imageRef.downloadUrl
        }
        return urlTask
    }

    fun GetFlowerShopURL(context: Context, picture: Uri, adress: String, putPlace: (String) -> Unit){
        var pic:Bitmap?
        val contentResolver = context.contentResolver
        val inputStream: InputStream? = contentResolver.openInputStream(picture)

        if(inputStream == null){
            Log.d("geturl", "Inputstream nije otvoren")
            return
        }

        pic = BitmapFactory.decodeStream(inputStream)
        if(pic == null) {
            Log.d("geturl", "Slika nije dekodirana")
            return
        }

        UploadFlowerShopPicture(pic, adress).addOnCompleteListener { task ->
            if (task.isSuccessful){
                val imageUrl = task.result.toString()
                putPlace(imageUrl)
                StoreFlowerShopPicture(imageUrl, adress)
            }
        }
    }

    fun StoreFlowerShopPicture(url: String, adress: String){
        store.collection("ListaCvecara").document(auth.currentUser!!.uid + adress).get().addOnSuccessListener {
            var fs = it.toObject<CvecaraData>()
            fs!!.ProfilePicture = url
            store.collection("ListaCvecara").document(auth.currentUser!!.uid + adress).set(fs).addOnSuccessListener {
                Log.d("storepicture", "slika uspesno uploadovana")
            }
                .addOnFailureListener{
                    Log.d("storepicture", "slika neuspesno uploadovana")
                }
        }
            .addOnFailureListener{
                Log.d("storepicture", "slika neuspesno uploadovana")
            }
    }

    private fun UploadFlowerShopPictures(picture: Bitmap?, adress: String, i: Int): Task<Uri> {
        Log.d("adding pic", "getting reference")
        val imageRef: StorageReference = storage.reference.child("FlowerShopPicture")
            .child(auth.currentUser!!.uid + adress).child("$i.jpg")
        val baos = ByteArrayOutputStream()
        val bitmap = picture
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        Log.d("adding pic", "starting task")
        val uploadTask = imageRef.putBytes(data)
        val urlTask = uploadTask.continueWithTask{
                task -> if(!task.isSuccessful){
            task.exception?.let {
                Log.d("upload", it.message.toString())
            }
        }
            imageRef.downloadUrl
        }
        return urlTask
    }

    fun GetFlowerShopURLs(context: Context, picture: Uri, adress: String, i: Int, putPlace: (String) -> Unit){
        Log.d("adding pic", "entering repos")
        var pic:Bitmap?
        val contentResolver = context.contentResolver
        val inputStream: InputStream? = contentResolver.openInputStream(picture)
        Log.d("adding pic", "creating inputstream")
        if(inputStream == null){
            Log.d("geturl", "Inputstream nije otvoren")
            return
        }
        Log.d("adding pic", "creating bitmap")
        pic = BitmapFactory.decodeStream(inputStream)
        if(pic == null) {
            Log.d("geturl", "Slika nije dekodirana")
            return
        }
        Log.d("adding pic", "uploading picture")
        UploadFlowerShopPictures(pic, adress, i).addOnCompleteListener { task ->
            if (task.isSuccessful){
                val imageUrl = task.result.toString()
                StoreFlowerShopPictures(imageUrl, adress, i){
                    putPlace(imageUrl)
                }
            }
        }
    }

    fun StoreFlowerShopPictures(url: String, adress: String, i: Int, putPlace: (String) -> Unit){
        Log.d("adding pic", "adding url")
        store.collection("ListaCvecara").document(auth.currentUser!!.uid + adress).get().addOnSuccessListener {
            var fs = it.toObject<CvecaraData>()
            val list = fs!!.Pictures.toTypedArray()
            list[i] = url
            fs!!.Pictures = list.toList()

            if (fs != null) {
                store.collection("ListaCvecara").document(auth.currentUser!!.uid + adress).set(fs).addOnSuccessListener {
                    putPlace(url)
                    Log.d("storepicture", "slika uspesno uploadovana")
                }
                    .addOnFailureListener{
                        Log.d("storepicture", "slika neuspesno uploadovana")
                    }
            }
        }
            .addOnFailureListener{
                Log.d("storepicture", "slika neuspesno uploadovana")
            }
    }

    private fun UploadUserPicture(picture: Bitmap?, id: String): Task<Uri> {
        val imageRef: StorageReference = storage.reference.child("UserPicture")
            .child(id).child(auth.currentUser!!.uid + ".jpg")
        val baos = ByteArrayOutputStream()
        val bitmap = picture
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = imageRef.putBytes(data)
        val urlTask = uploadTask.continueWithTask{
                task -> if(!task.isSuccessful){
            task.exception?.let {
                Log.d("upload", it.message.toString())
            }
        }
            imageRef.downloadUrl
        }
        return urlTask
    }

    fun GetUserURL(context: Context, picture: Uri, id: String){
        var pic:Bitmap?
        val contentResolver = context.contentResolver
        val inputStream: InputStream? = contentResolver.openInputStream(picture)

        if(inputStream == null){
            Log.d("geturl", "Inputstream nije otvoren")
            return
        }

        pic = BitmapFactory.decodeStream(inputStream)
        if(pic == null) {
            Log.d("geturl", "Slika nije dekodirana")
            return
        }

        UploadUserPicture(pic, id).addOnCompleteListener { task ->
            if (task.isSuccessful){
                val imageUrl = task.result.toString()
                StoreUserPicture(imageUrl, id)
            }
        }
    }

    fun StoreUserPicture(url: String, id: String){
        store.collection("Predlozi").document(id + auth.currentUser!!.uid).get().addOnSuccessListener {
            var ur = it.toObject<UserRecomendationData>()
            ur!!.Picture = url
            store.collection("Predlozi").document(id + auth.currentUser!!.uid).set(ur).addOnSuccessListener {
                Log.d("storepicture", "slika uspesno uploadovana")
            }
                .addOnFailureListener{
                    Log.d("storepicture", "slika neuspesno uploadovana")
                }
        }
            .addOnFailureListener{
                Log.d("storepicture", "slika neuspesno uploadovana")
            }
    }
}
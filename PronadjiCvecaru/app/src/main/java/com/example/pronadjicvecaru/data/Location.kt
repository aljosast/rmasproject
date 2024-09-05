package com.example.pronadjicvecaru.data

object Location {

    private var lat: Double = 0.0
    private var lng: Double = 0.0

    fun Set(x:Double, y:Double){
        lat = x
        lng = y
    }
    fun Getx(): Double{
        return lat
    }
    fun Gety(): Double{
        return lng
    }
}
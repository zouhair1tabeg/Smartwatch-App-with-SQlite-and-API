package com.example.smartwatchdarkmodeapp

import retrofit2.http.GET

interface ApiService {
    @GET("WatchAPI/readAll.php")
    fun getWatch(): retrofit2.Call<List<SmartWatch>>
}
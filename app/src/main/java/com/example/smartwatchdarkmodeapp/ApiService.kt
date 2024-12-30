package com.example.smartwatchdarkmodeapp

import android.telecom.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class AddResponse(
    val status: Int,
    val status_message: String
)

interface ApiService {
    @GET("WatchAPI/readAll.php")
    fun getWatch(): retrofit2.Call<List<SmartWatch>>

    @POST("WatchAPI/create.php")
    fun addWatch(@Body watch: SmartWatch): retrofit2.Call<AddResponse>
}
package com.example.smartwatchdarkmodeapp

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SmartWatches : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_smart_watches)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val smartList = findViewById<ListView>(R.id.lv)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://apiyes.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        apiService.getWatch().enqueue(object : Callback<List<SmartWatch>> {
            override fun onResponse(
                call: Call<List<SmartWatch>>,
                response: Response<List<SmartWatch>>
            ) {
                if (response.isSuccessful) {
                    val watches = response.body() ?: emptyList()

                    // Set up the adapter with the fetched data
                    val adapter = adapter(this@SmartWatches, watches)
                    smartList.adapter = adapter
                } else {
                }
            }

            override fun onFailure(call: Call<List<SmartWatch>>, t: Throwable) {
                Toast.makeText(this@SmartWatches, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
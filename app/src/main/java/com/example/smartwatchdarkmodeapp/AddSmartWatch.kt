package com.example.smartwatchdarkmodeapp

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddSmartWatch : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_smart_watch)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        getData()
    }

    private fun getData(){
        val retrofit2 = Retrofit.Builder()
            .baseUrl("https://apiyes.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService2 = retrofit2.create(ApiService::class.java)

        val editTextName = findViewById<EditText>(R.id.watchName)
        val editTextPrice = findViewById<EditText>(R.id.watchPrice)
        val editTextImageUrl = findViewById<EditText>(R.id.watchImage)
        val WaterResistance = findViewById<CheckBox>(R.id.watchCheck)
        val buttonAddWatch = findViewById<Button>(R.id.btnAddWatch)

        buttonAddWatch.setOnClickListener {
            val name = editTextName.text.toString().trim()
            val priceStr = editTextPrice.text.toString().trim()
            val imageUrl = editTextImageUrl.text.toString().trim()
            val WaterR = WaterResistance.isChecked

            if (name.isEmpty() || priceStr.isEmpty() || imageUrl.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                val price = priceStr.toDouble()

                val smartwatch = SmartWatch(0, name, price, WaterR ,imageUrl)

                apiService2.addWatch(smartwatch).enqueue(object : Callback<AddResponse> {
                    override fun onResponse(call: Call<AddResponse>, response: Response<AddResponse>) {
                        if (response.isSuccessful) {
                            val addResponse = response.body()
                            if (addResponse != null) {
                                Toast.makeText(applicationContext, addResponse.status_message, Toast.LENGTH_LONG).show()
                                if (addResponse.status == 1) {
                                    getData()
                                    finish()
                                }
                            }
                        } else {
                            Toast.makeText(applicationContext, "Failed to add Smartwatch", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<AddResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }
}
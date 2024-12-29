package com.example.smartwatchdarkmodeapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val sendButton = findViewById<Button>(R.id.singUpBtn)
        sendButton.setOnClickListener {
            val nom = findViewById<EditText>(R.id.EDnom).text.toString()
            val prenom = findViewById<EditText>(R.id.EDprenom).text.toString()
            val mail = findViewById<EditText>(R.id.EDmail).text.toString()
            val password = findViewById<EditText>(R.id.EDpass).text.toString()


            val user = Users(nom = nom, prenom = prenom, mail = mail, password = password)
            val db = UsersDataBase.getDataBase(this)

            db.usersDao().insertUser(user)

            Toast.makeText(this, "Utilisateur créé avec succès", Toast.LENGTH_SHORT).show()

            finish()
        }


    }
}
package com.example.smartwatchdarkmodeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val signupBtn = findViewById<Button>(R.id.singBtn)
        signupBtn.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        val loginButton = findViewById<Button>(R.id.loginBtn)
        loginButton.setOnClickListener {
            val email = findViewById<EditText>(R.id.loginMial).text.toString()
            val password = findViewById<EditText>(R.id.loginPass).text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.d("Login", "Email saisi: $email")
            Log.d("Login", "Mot de passe saisi: $password")

            // Utilisation des coroutines pour exécuter la requête dans un thread en arrière-plan
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val db = UsersDataBase.getDataBase(applicationContext)
                    val user = db.usersDao().getUserByEmailAndPassword(email, password)

                    if (user != null) {
                        Log.d("Login", "Utilisateur trouvé: ${user.nom} ${user.prenom}")
                        withContext(Dispatchers.Main) {
                            val intent = Intent(this@MainActivity, SmartWatches::class.java)
                            intent.putExtra("userId", user.id)
                            intent.putExtra("username", user.nom)
                            intent.putExtra("userprenom", user.prenom)
                            startActivity(intent)
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@MainActivity, "Identifiants incorrects", Toast.LENGTH_SHORT).show()
                        }
                        Log.d("Login", "Aucun utilisateur trouvé avec cet email et mot de passe.")
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "Erreur d'accès à la base de données", Toast.LENGTH_SHORT).show()
                    }
                    e.printStackTrace()
                }
            }
        }
    }
}

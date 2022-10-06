package com.example.lugares

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.lugares.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        //metodo de login
        binding.btLogin.setOnClickListener(){
            hacerlogin()
        }

        //metodo de registro
        binding.btRegister.setOnClickListener(){
            hacerRegister()
        }
    }

    private fun hacerRegister(){
        var email = binding.etMail.text.toString()
        var clave = binding.etClave.text.toString()

        //regitstra

        auth.createUserWithEmailAndPassword(email, clave)
            .addOnCompleteListener(this) { task ->
                if(!task.isSuccessful){
                    Log.d("creando usuario", "fallo")
                    Toast.makeText(baseContext, "fallo", Toast.LENGTH_LONG).show()
                }

                Log.d("creando usuario", "registrado")
                val user = auth.currentUser
                if(user != null){
                    actualiza(user)
                }
            }
    }

    private fun hacerlogin(){
        var email = binding.etMail.text.toString()
        var clave = binding.etClave.text.toString()

        // se hace login
        auth.createUserWithEmailAndPassword(email, clave)
            .addOnCompleteListener(this) { task ->
                if(!task.isSuccessful){
                    Log.d("autenticando", "fallo")
                    Toast.makeText(baseContext, "fallo", Toast.LENGTH_LONG).show()
                }

                Log.d("autenticando", "autenticado")
                val user = auth.currentUser
            }
    }

    private fun actualiza(user: FirebaseUser?){
        if(user != null){
            val intent = Intent(this, Principal::class.java)
            startActivity(intent)
        }
    }

    public override fun onStart(){
        super.onStart()
        val usuario = auth.currentUser
        actualiza(usuario)
    }
}
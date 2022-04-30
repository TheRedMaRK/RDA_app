package com.example.rda_app

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rda_app.databinding.ActivityLoginBinding
import com.example.rda_app.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivityMainBinding

    //FireStore
    private lateinit var fStore: FirebaseFirestore

    //progressDialog
    private lateinit var progressDialog: ProgressDialog

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        val type = intent.getStringExtra("type")
        binding.lblUserType.text = type

        //handle click, logout
        binding.btnLogout.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }
}
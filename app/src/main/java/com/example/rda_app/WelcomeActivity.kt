package com.example.rda_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.concurrent.schedule

class WelcomeActivity : AppCompatActivity() {

    // Firestore variable
    private lateinit var fStore: FirebaseFirestore

    // FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        Timer().schedule(1000) {
            checkUser()
        }
    }

    private fun checkUser() {
        //if user is already logged in go to Main activity
        //get current user
        if (firebaseAuth.currentUser != null) {
            val userId = firebaseAuth.currentUser!!.uid
            val email = firebaseAuth.currentUser!!.email
            //Code to get if userLogin variable true or false from the database
            fStore = FirebaseFirestore.getInstance()
            fStore.collection("users").document(userId).get()
                .addOnCompleteListener { task: Task<DocumentSnapshot> ->
                    val document = task.result
                    val username = document.get("fullName").toString()
                    val vehicleId = document.get("regNo").toString()

                    Toast.makeText(this, "Logged in as $email", Toast.LENGTH_SHORT).show()

                    when (document.get("type").toString()) {
                        "admin" -> {
                            val intent = Intent(this, WebmasterActivity::class.java)
                            intent.putExtra("name", username)
                            startActivity(intent)
                            finish()
                        }
                        "driver" -> {
                            val intent = Intent(this, HomeUserActivity::class.java)
                            intent.putExtra("name", username)
                            intent.putExtra("vid", vehicleId)
                            startActivity(intent)
                            finish()
                        }
                        "police" -> {
                            val intent = Intent(this, HomeStaffActivity::class.java)
                            intent.putExtra("user", "police")
                            intent.putExtra("name", username)
                            startActivity(intent)
                            finish()
                        }
                        "rda" -> {
                            val intent = Intent(this, HomeStaffActivity::class.java)
                            intent.putExtra("user", "rda")
                            intent.putExtra("name", username)
                            startActivity(intent)
                            finish()
                        }
                        "insurance" -> {
                            val intent = Intent(this, HomeStaffActivity::class.java)
                            intent.putExtra("user", "insurance")
                            intent.putExtra("name", username)
                            startActivity(intent)
                            finish()
                        }
                    }
                }

        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
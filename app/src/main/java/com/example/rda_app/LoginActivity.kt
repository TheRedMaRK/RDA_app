package com.example.rda_app

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.rda_app.databinding.ActivityLoginBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivityLoginBinding

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
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Top bar
        supportActionBar!!.title = "Login"

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Logging In...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        //checkUser()

        //handle click, open register activity
        binding.lblNoAccount.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }

        //handle click, begin login
        binding.btnLogin.setOnClickListener {
            validateData()
        }

        binding.btnDev.setOnClickListener {
            val intent = Intent(this, TestingActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun validateData() {
        //get data
        email = binding.txtEmail.text.toString().trim()
        password = binding.txtPassword.text.toString().trim()

        //Validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format
            binding.txtEmail.error = "Invalid email Format"

        }
        else if(TextUtils.isEmpty(password)){
            //No password entered
            binding.txtPassword.error = "Please enter password"
        }
        else{
            //login if data is validated
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {

        //show progress
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //login Successful
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                val userId = firebaseUser.uid

                fStore = FirebaseFirestore.getInstance()
                fStore.collection("users").document(userId).get().addOnCompleteListener { task: Task<DocumentSnapshot> ->
                    val document = task.result
                    val username = document.get("fullName").toString()
                    val vehicleId = document.get("regNo").toString()

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

                    Toast.makeText(this, "Logged in as $email", Toast.LENGTH_SHORT).show()
                }
                progressDialog.dismiss()
            }
            .addOnFailureListener { e->
                //login failed
                progressDialog.dismiss()
                Toast.makeText(this,"${e.message}",Toast.LENGTH_SHORT).show()
            }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this@LoginActivity, "Logged in as $msg", Toast.LENGTH_LONG).show()
    }
}
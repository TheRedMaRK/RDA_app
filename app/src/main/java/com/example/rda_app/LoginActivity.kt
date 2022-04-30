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
                    val type = document.get("type").toString()

                    when (type) {
                        "admin" -> showToast("Webmaster")
                        "driver" -> showToast("Driver")
                        "police" -> showToast("Police")
                        else -> showToast("---INVALID---")
                    }

                    Toast.makeText(this,"Logged in as $email",Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@LoginActivity,MainActivity::class.java)
                    intent.putExtra("type", type)
                    startActivity(intent)
                    finish()
                }
                    .addOnFailureListener { e->
                        //login failed
                        progressDialog.dismiss()
                        Toast.makeText(this,"Login failed due to ${e.message}",Toast.LENGTH_SHORT).show()
                    }
            }
    }

    private fun showToast(msg: String) {

        Toast.makeText(this@LoginActivity, "Logged in as $msg", Toast.LENGTH_LONG).show()
    }
}
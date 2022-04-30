package com.example.rda_app

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.rda_app.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivityRegisterBinding

    //FireStore
    private lateinit var fStore:FirebaseFirestore

    //ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure ProgressDialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Creating new account...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        //Redirect back to login activity if user already have an account
        binding.lblHaveAccount.setOnClickListener {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        //handle click, begin register
        binding.btnRegister.setOnClickListener {

            //validate data
            validateData()
        }
    }

    private fun validateData() {

        email = binding.txtEmail.text.toString().trim()
        password = binding.txtPassword.text.toString().trim()

        //Validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //invalid email format
            binding.txtEmail.error = "Invalid email format"
        } else if (TextUtils.isEmpty(password)) {
            //Password is not entered
            binding.txtPassword.error = "Please enter password"
        } else if (password.length < 6) {
            //password is short
            binding.txtPassword.error = "Password must be at least more than 6 characters"
        } else {
            firebaseRegister()
        }
    }

    private fun firebaseRegister() {

        //show progress
        progressDialog.show()

        //create account
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //Successfully registered
                progressDialog.dismiss()
                //get current user
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                val userId = firebaseUser.uid

                val fullName = binding.txtPhone.text.toString()
                val regNo = binding.txtRegNo.text.toString()
                val insurance = binding.txtInsurance.text.toString()
                val phone =binding.txtPhone.text.toString()
                val type = "driver"

                fStore = FirebaseFirestore.getInstance()
                val register = Register(fullName, regNo, insurance, phone, email, type)
                fStore.collection("users").document(userId).set(register)

                Toast.makeText(this, "Registered with $email", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e ->
                //Failed to register
                Toast.makeText(this, "Registration failed due to ${e.message}", Toast.LENGTH_SHORT)
                    .show()

            }
    }

    override fun onSupportNavigateUp(): Boolean {
        //Go back to previous activity
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
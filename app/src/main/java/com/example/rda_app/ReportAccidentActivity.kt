package com.example.rda_app

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.rda_app.databinding.ActivityReportAccidentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class ReportAccidentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReportAccidentBinding
    private lateinit var imageUri : Uri
    var imageSelected = false

    //Database variable declaration
    private lateinit var fStore: FirebaseFirestore

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportAccidentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Top bar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Report Accident"

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        // Firebase fireStore auth
        fStore = FirebaseFirestore.getInstance()

        binding.btnAddPhotos.setOnClickListener {
            selectImage()
        }

        // Submit button
        binding.btnSubmit.setOnClickListener {
            submitReport()
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {
            imageUri = data?.data!!
            binding.imgUpload.setImageURI(imageUri)
            imageSelected = true
        }
    }

    private fun submitReport() {
        // Setting the image upload
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading file...")
        progressDialog.setCancelable(false)
        //progressDialog.show()

        // Setting the uploaded time as part of image file name
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()

        val firebaseUser = firebaseAuth.currentUser
        firebaseUser!!.email
        val userId = firebaseUser.uid

        // Filename for the image uploaded by the user
        val fileName = userId + " : " + formatter.format(now)

        // Reference to firebase storage
        val storageReference = FirebaseStorage.getInstance().getReference("image/$fileName")

        // Uploading file to firebase cloud storage
        if (imageSelected) {
            storageReference.putFile(imageUri).
            addOnSuccessListener {
                binding.imgUpload.setImageURI(null)
                Toast.makeText(this@ReportAccidentActivity, "Successfully uploaded image!", Toast.LENGTH_SHORT).show()
                //if (progressDialog.isShowing) progressDialog.dismiss()
            }.addOnFailureListener{
                //if (progressDialog.isShowing) progressDialog.dismiss()
                Toast.makeText(this@ReportAccidentActivity, "Upload failed", Toast.LENGTH_SHORT).show()
            }
        }

        // Other report values

        val location = binding.txtLocation.text.toString()
        val date = binding.txtDate.text.toString()
        val time = binding.txtTime.text.toString()
        val incidentDetails = binding.txtIncidentDetails.text.toString()
        val approved = false

        val report = Report(
            userId,
            location,
            date,
            time,
            incidentDetails,
            approved
        )

        fStore.collection("reports").document().set(report)

        Toast.makeText(this@ReportAccidentActivity, "Report successful!", Toast.LENGTH_SHORT).show()

        finish()
    }
}
package com.example.rda_app

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
    private lateinit var txtMeetingDate: TextView

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

        // Date and time pickers
        txtMeetingDate = binding.txtDate

        val myCalendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(myCalendar)
        }

        //Meeting Time Text View pops up the Time Picker to select the Meeting Date
        binding.txtTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                //set time to Text View
                binding.txtTime.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }

        //Meeting Date Text View pops up the calendar to select the Meeting Date
        binding.txtDate.setOnClickListener {
            DatePickerDialog(
                this,
                datePicker,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
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
            approved,
            reportId = ""
        )

        fStore.collection("reports").document().set(report)

        Toast.makeText(this@ReportAccidentActivity, "Report successful!", Toast.LENGTH_SHORT).show()

        finish()
    }

    // Related to date-time picker
    private fun updateLabel(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        txtMeetingDate.text = sdf.format(myCalendar.time)
    }
}
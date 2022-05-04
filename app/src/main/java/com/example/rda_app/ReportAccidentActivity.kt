package com.example.rda_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.rda_app.databinding.ActivityReportAccidentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ReportAccidentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReportAccidentBinding

    //Database variable declaration
    private lateinit var fStore: FirebaseFirestore

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportAccidentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        // Firebase fireStore auth
        fStore = FirebaseFirestore.getInstance()

        // Submit button
        binding.btnSubmit.setOnClickListener {
            submitReport()
        }
    }

    private fun submitReport() {
        val firebaseUser = firebaseAuth.currentUser
        firebaseUser!!.email
        val userId = firebaseUser.uid

        val location = binding.txtLocation.text.toString()
        val date = binding.txtDate.text.toString()
        val time = binding.txtTime.text.toString()
        val incidentDetails = binding.txtIncidentDetails.text.toString()

        val report = Report(
            userId,
            location,
            date,
            time,
            incidentDetails
        )

        fStore.collection("reports").document().set(report)

        Toast.makeText(this@ReportAccidentActivity, "Report successful!", Toast.LENGTH_SHORT).show()

        finish()
    }
}
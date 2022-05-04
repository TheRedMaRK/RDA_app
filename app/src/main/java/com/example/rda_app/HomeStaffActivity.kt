package com.example.rda_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.rda_app.databinding.ActivityHomeStaffBinding
import com.example.rda_app.databinding.ActivityHomeUserBinding
import com.example.rda_app.databinding.ActivityTestingBinding

class HomeStaffActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeStaffBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeStaffBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // See if the staff member is a police or other
        when (intent.getStringExtra("user")) {
            "rda" -> {
                binding.btnDetailedReports.visibility = View.INVISIBLE
                binding.lblLoggedIn.text = "LOGGED IN AS RDA STAFF"
            }
            "insurance" -> {
                binding.btnDetailedReports.visibility = View.INVISIBLE
                binding.lblLoggedIn.text = "LOGGED IN AS INSURANCE STAFF"
            }
            "police" -> {
                binding.lblLoggedIn.text = "LOGGED IN AS POLICE"
            }
        }

        // get username
        binding.lblUserValue.text = intent.getStringExtra("name")

        binding.btnDetailedReports.setOnClickListener {
            val intent = Intent(this, DetailedReportsActivity::class.java)
            startActivity(intent)
        }
    }
}
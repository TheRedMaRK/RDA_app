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
        val user = intent.getStringExtra("user")

        if (user == "staff") {
            binding.btnDetailedReports.visibility = View.INVISIBLE
            binding.lblLoggedIn.text = "LOGGED IN AS STAFF"
        }
        else {
            binding.lblLoggedIn.text = "LOGGED IN AS POLICE"
        }

        binding.btnDetailedReports.setOnClickListener {
            val intent = Intent(this, DetailedReportsActivity::class.java)
            startActivity(intent)
        }
    }
}
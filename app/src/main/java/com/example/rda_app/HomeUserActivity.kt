package com.example.rda_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.rda_app.databinding.ActivityHomeUserBinding
import com.example.rda_app.databinding.ActivityLoginBinding

class HomeUserActivity : AppCompatActivity() {
    // view binding
    private lateinit var binding: ActivityHomeUserBinding
    //Gives the ability open the drawer by the Toggle button
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Top bar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Home - User"

        // get username and vehicle id
        binding.lblUserValue.text = intent.getStringExtra("name")
        binding.lblVehicleIdValue.text = intent.getStringExtra("vid")

        binding.btnReportAccident.setOnClickListener {
            val intent = Intent(this, ReportAccidentActivity::class.java)
            startActivity(intent)
        }

        binding.btnMyReport.setOnClickListener {
            val intent = Intent(this, MyReportsActivity::class.java)
            startActivity(intent)
        }
    }
}
package com.example.rda_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rda_app.databinding.ActivityViewUsersBinding
import com.example.rda_app.databinding.ActivityWebmasterBinding

class ViewUsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewUsersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Top bar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "View Users"

        // Go to activities
        binding.btnDrivers.setOnClickListener {
            startActivity(Intent(this, ViewDriversActivity::class.java))
        }
        binding.btnPolice.setOnClickListener {
            startActivity(Intent(this, ViewPoliceActivity::class.java))
        }
        binding.btnRDA.setOnClickListener {
            startActivity(Intent(this, ViewRDAActivity::class.java))
        }
        binding.btnInsurance.setOnClickListener {
            startActivity(Intent(this, ViewInsuranceActivity::class.java))
        }
    }
}
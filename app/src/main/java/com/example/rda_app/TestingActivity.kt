package com.example.rda_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rda_app.databinding.ActivityHomeStaffBinding
import com.example.rda_app.databinding.ActivityTestingBinding

class TestingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTestingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Top bar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Testing Activity"

        binding.btnUser.setOnClickListener {
            val intent = Intent(this, HomeUserActivity::class.java)
            startActivity(intent)
        }
        binding.btnStaff.setOnClickListener {
            val intent = Intent(this, HomeStaffActivity::class.java)
            intent.putExtra("user", "staff")
            startActivity(intent)
        }
        binding.btnPolice.setOnClickListener {
            val intent = Intent(this, HomeStaffActivity::class.java)
            intent.putExtra("user", "police")
            startActivity(intent)
        }
    }
}
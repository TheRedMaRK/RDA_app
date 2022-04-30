package com.example.rda_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rda_app.databinding.ActivityHomeUserBinding
import com.example.rda_app.databinding.ActivityLoginBinding

class HomeUserActivity : AppCompatActivity() {
    // view binding
    private lateinit var binding: ActivityHomeUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnReportAccident.setOnClickListener {
            val intent = Intent(this, ReportAccidentActivity::class.java)
            startActivity(intent)
        }
    }
}
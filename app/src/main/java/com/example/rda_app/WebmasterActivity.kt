package com.example.rda_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rda_app.databinding.ActivityHomeUserBinding
import com.example.rda_app.databinding.ActivityWebmasterBinding

class WebmasterActivity : AppCompatActivity() {

    lateinit var binding: ActivityWebmasterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebmasterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get username
        binding.lblUserValue.text = intent.getStringExtra("name")
    }
}
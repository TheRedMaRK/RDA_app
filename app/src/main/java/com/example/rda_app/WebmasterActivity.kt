package com.example.rda_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rda_app.databinding.ActivityTestingBinding
import com.example.rda_app.databinding.ActivityWebmasterBinding

class WebmasterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebmasterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebmasterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get username
        binding.lblUserValue.text = intent.getStringExtra("name")

        binding.btnAddUsers.setOnClickListener {
            val intent = Intent(this, AddUser::class.java)
            startActivity(intent)
        }
    }
}
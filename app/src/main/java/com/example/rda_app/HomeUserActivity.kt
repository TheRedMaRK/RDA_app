package com.example.rda_app

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.rda_app.databinding.ActivityHomeUserBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home_user.*

class HomeUserActivity : AppCompatActivity() {
    // view binding
    private lateinit var binding: ActivityHomeUserBinding
    //Gives the ability open the drawer by the Toggle button
    private lateinit var toggle: ActionBarDrawerToggle
    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

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

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        //For the drawerLayout to work we should include: id 'kotlin-android-extensions' in build.gradle(Module) plugins section
        toggle = ActionBarDrawerToggle(this@HomeUserActivity, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.miItem1 -> {
                    firebaseAuth.signOut()
                    startActivity(Intent(this,LoginActivity::class.java))
                    finish()
                }
            }
            true
        }

    }

    // This function is needed for the navigation toggle to work
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
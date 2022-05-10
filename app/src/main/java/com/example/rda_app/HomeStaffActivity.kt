package com.example.rda_app

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.rda_app.databinding.ActivityHomeStaffBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home_user.*

class HomeStaffActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeStaffBinding
    //Gives the ability open the drawer by the Toggle button
    private lateinit var toggle: ActionBarDrawerToggle
    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeStaffBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Top bar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Home - Staff"

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

        binding.btnNewReports.setOnClickListener {
            val intent = Intent(this, NewReportsActivity::class.java)
            startActivity(intent)
        }
        binding.btnViewedReports.setOnClickListener {
            val intent = Intent(this, ViewReportsActivity::class.java)
            startActivity(intent)
        }
        binding.btnDetailedReports.setOnClickListener {
            val intent = Intent(this, DetailedReportsActivity::class.java)
            startActivity(intent)
        }

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        //For the drawerLayout to work we should include: id 'kotlin-android-extensions' in build.gradle(Module) plugins section
        toggle = ActionBarDrawerToggle(this@HomeStaffActivity, drawerLayout, R.string.open, R.string.close)
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
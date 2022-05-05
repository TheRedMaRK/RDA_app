package com.example.rda_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MyReportsActivity : AppCompatActivity() {

    // Database reference
    private lateinit var fStore: FirebaseFirestore

    // FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_reports)
        // Top bar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Add Users"

        // getting the recyclerview by its id
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerMyReports)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<Report>()

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        // this is the only way
        getData { result ->

            val userId = result[4]
            val location = result[2]
            val date = result[0]
            val time = result[3]
            val incidentDetails = result[1]

            // this creates a vertical layout Manager
            recyclerView.layoutManager = LinearLayoutManager(this)

            // This loop will create 20 Views containing
            // the image with the count of view
            data.add(Report(userId, location, date, time, incidentDetails))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = MyReportsAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerView.adapter = adapter
    }

    // This function is used to callback values retrieved inside the onSuccessListener of firebase retrieval
    private fun getData(callback: (ArrayList<String>) -> Unit) {

        val firebaseUser = firebaseAuth.currentUser
        firebaseUser!!.email
        val userId = firebaseUser.uid

        // Data array and map
        var dataArray: ArrayList<String>
        var dataMap: HashMap<String, String>

        fStore = FirebaseFirestore.getInstance()
        // Database reference to the "meeting" collection
        val reportsRef = fStore.collection("reports")

        val query = reportsRef.whereEqualTo("userId", userId)
        query.get().addOnSuccessListener { documents ->
            for (document in documents) {
                // id is the meeting id
                // println(document.id)
                // data is an array which contains all the data inside the meeting
                // println(document.data)
                dataMap = document.data as HashMap<String, String>
                dataArray = ArrayList(dataMap.values)
                callback(dataArray)
            }
        }

    }
}
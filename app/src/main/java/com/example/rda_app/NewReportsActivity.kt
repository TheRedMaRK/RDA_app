package com.example.rda_app

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class NewReportsActivity : AppCompatActivity(), NewReportsAdapter.OnItemClickListener {

    // Database reference
    private lateinit var fStore: FirebaseFirestore

    // FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_reports)
        // Top bar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "New Reports"

        // getting the recyclerview by its id
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerNewReports)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<Report>()

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        // this is the only way
        getData { result ->

            val userId = result[6]
            val location = result[4] //4
            val date = result[0] //0
            val time = result[5] //5
            val incidentDetails = result[3] //3
            val approved = result[1] as Boolean
            val reportId = result[7]

            // this creates a vertical layout Manager
            recyclerView.layoutManager = LinearLayoutManager(this)

            // This loop will create 20 Views containing
            // the image with the count of view
            data.add(Report(userId, location, date, time, incidentDetails, approved, reportId))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = NewReportsAdapter(data, this)

        // Setting the Adapter with the recyclerview
        recyclerView.adapter = adapter

        swipeRefreshLayout = findViewById(R.id.swipe)

        swipeRefreshLayout.setOnRefreshListener {
            finish()
            startActivity(intent)
        }
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

        val query = reportsRef.whereEqualTo("approved", false)
        query.get().addOnSuccessListener { documents ->
            for (document in documents) {
                // id is the meeting id
                // println(document.id)
                // data is an array which contains all the data inside the meeting
                // println(document.data)
                dataMap = document.data as HashMap<String, String>
                dataArray = ArrayList(dataMap.values)
                dataArray.add(document.id)
                callback(dataArray)
            }
        }

    }

    // This handles the onclick events for the recyclerview
    override fun onItemClick(position: Int, text: String, id: String) {
        Toast.makeText(this, "$text $position clicked", Toast.LENGTH_SHORT).show()
        println(id)
        if (text == "Approve") {
            // Update one field, creating the document if it does not already exist.
            val data = hashMapOf("approved" to true)
            fStore.collection("reports").document(id)
                .set(data, SetOptions.merge())
            Toast.makeText(this, "Report: $id approved", Toast.LENGTH_SHORT).show()
        }
        else if (text == "Reject") {
            fStore.collection("reports").document(id)
                .delete()
                .addOnSuccessListener { Toast.makeText(this, "Report: $id rejected", Toast.LENGTH_SHORT).show() }
                .addOnFailureListener { Toast.makeText(this, "Report: $id rejection failed", Toast.LENGTH_SHORT).show() }
        }
    }
}
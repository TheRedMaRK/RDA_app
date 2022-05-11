package com.example.rda_app

import android.os.Bundle
import android.text.Editable
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rda_app.databinding.ActivityDetailedReportsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DetailedReportsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailedReportsBinding

    // Database reference
    private lateinit var fStore: FirebaseFirestore

    // FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailedReportsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Top bar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Detailed Reports"

        // Initialize bindings
        val spinnerFilter = binding.spinnerFilter

        // Create an ArrayAdapter
        val adapterFilter = ArrayAdapter.createFromResource(
            this,
            R.array.filters,
            android.R.layout.simple_spinner_item
        )
        val adapterChart = ArrayAdapter.createFromResource(
            this,
            R.array.chart,
            android.R.layout.simple_spinner_item
        )

        // Specify the layout to use when the list of choices appears
        adapterFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterChart.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        spinnerFilter.adapter = adapterFilter

        binding.btnGenerateReports.setOnClickListener {

            // getting the recyclerview by its id
            val recyclerView = binding.recyclerDetailedReports

            // ArrayList of class ItemsViewModel
            val data = ArrayList<Report>()

            //init firebaseAuth
            firebaseAuth = FirebaseAuth.getInstance()

            val filterText = binding.txtFilter
            val filterType = spinnerFilter.selectedItem.toString()

            // this is the only way
            getData(filterText.text, filterType) { result ->

                val userId = result[6]
                val location = result[4]
                val date = result[0]
                val time = result[5]
                val incidentDetails = result[3]

                // this creates a vertical layout Manager
                recyclerView.layoutManager = LinearLayoutManager(this)

                // This loop will create 20 Views containing
                // the image with the count of view
                data.add(Report(userId, location, date, time, incidentDetails))
            }

            // This will pass the ArrayList to our Adapter
            val adapter = DetailedReportsAdapter(data)

            // Setting the Adapter with the recyclerview
            recyclerView.adapter = adapter
        }
    }

    // This function is used to callback values retrieved inside the onSuccessListener of firebase retrieval
    private fun getData(filterText: Editable, filterType: String, callback: (ArrayList<String>) -> Unit) {

        val firebaseUser = firebaseAuth.currentUser
        firebaseUser!!.email
        firebaseUser.uid

        // Data array and map
        var dataArray: ArrayList<String>
        var dataMap: HashMap<String, String>

        fStore = FirebaseFirestore.getInstance()
        // Database reference to the "meeting" collection
        val reportsRef = fStore.collection("reports")

        val query = reportsRef.whereEqualTo(filterType, filterText.toString())
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
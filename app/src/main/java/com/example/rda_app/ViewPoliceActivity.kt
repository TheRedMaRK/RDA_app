package com.example.rda_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ViewPoliceActivity : AppCompatActivity() {

    // Database reference
    private lateinit var fStore: FirebaseFirestore

    // FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_police)

        // Top bar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "View Police"

        // getting the recyclerview by its id
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewPolice)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<Police>()

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        // this is the only way
        getData { result ->

            val username = result[3] //
            val email = result[5] //
            val address = result[1] //
            val phone = result[2] //
            val district = result[0] //
            val sp = result[4] //

            // this creates a vertical layout Manager
            recyclerView.layoutManager = LinearLayoutManager(this)

            // This loop will create 20 Views containing
            // the image with the count of view
            data.add(Police(username, email, address, phone, district, sp))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = ViewPoliceAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerView.adapter = adapter
    }

    // This function is used to callback values retrieved inside the onSuccessListener of firebase retrieval
    private fun getData(callback: (ArrayList<String>) -> Unit) {

        val firebaseUser = firebaseAuth.currentUser
        firebaseUser!!.email
        firebaseUser.uid

        // Data array and map
        var dataArray: ArrayList<String>
        var dataMap: HashMap<String, String>

        fStore = FirebaseFirestore.getInstance()
        // Database reference to the "meeting" collection
        val usersRef = fStore.collection("users")

        val query = usersRef.whereEqualTo("type", "police")
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
}
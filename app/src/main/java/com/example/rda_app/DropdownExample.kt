package com.example.rda_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast

class DropdownExample : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private lateinit var spinner2: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dropdown_example)

        // Initialize spinner
        spinner = findViewById(R.id.spinner)

        // Create an ArrayAdapter
        val adapter = ArrayAdapter.createFromResource(this,
            R.array.filters, android.R.layout.simple_spinner_item)
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        spinner.adapter = adapter

    }

    fun getValues(view: View) {

        //Initialize spinners
        spinner = findViewById(R.id.spinner)
        spinner2 = findViewById(R.id.spinner2)

        Toast.makeText(this, "Spinner 1 " + spinner.selectedItem.toString() +
                "\nSpinner 2 " + spinner2.selectedItem.toString(), Toast.LENGTH_LONG).show()
    }
}
package com.example.rda_app

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.rda_app.databinding.ActivityDetailedReportsBinding

class DetailedReportsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailedReportsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailedReportsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Top bar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Detailed Reports"

        // Initialize bindings
        val spinnerFilter = binding.spinnerFilter
        val spinnerChart = binding.spinnerChart

        // Create an ArrayAdapter
        val adapterFilter = ArrayAdapter.createFromResource(this, R.array.filters, android.R.layout.simple_spinner_item)
        val adapterChart = ArrayAdapter.createFromResource(this, R.array.chart, android.R.layout.simple_spinner_item)

        // Specify the layout to use when the list of choices appears
        adapterFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterChart.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        spinnerFilter.adapter = adapterFilter
        spinnerChart.adapter = adapterChart

        binding.btnGenerateChart.setOnClickListener {
            binding.textView2.text = "Filter: " + spinnerFilter.selectedItem.toString() + "\nChart type: " + spinnerChart.selectedItem.toString()
        }
    }
}
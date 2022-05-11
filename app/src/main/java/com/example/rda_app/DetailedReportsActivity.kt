package com.example.rda_app

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.rda_app.databinding.ActivityDetailedReportsBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.activity_detailed_reports.*

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
        spinnerChart.adapter = adapterChart

        binding.btnGenerateChart.setOnClickListener {
            binding.textView2.text =
                "Filter: " + spinnerFilter.selectedItem.toString() + "\nChart type: " + spinnerChart.selectedItem.toString()
        }

        // Charts

        //Part1
        val entries = ArrayList<Entry>()

        //Part2
        entries.add(Entry(1f, 10f))
        entries.add(Entry(2f, 2f))
        entries.add(Entry(3f, 7f))
        entries.add(Entry(4f, 20f))
        entries.add(Entry(5f, 16f))

        //Part3
        val vl = LineDataSet(entries, "My Type")

        //Part4
        vl.setDrawValues(false)
        vl.setDrawFilled(true)
        vl.lineWidth = 3f
        vl.fillColor = R.color.primaryColor
        vl.fillAlpha = R.color.secondaryColor

        //Part5
        lineChart.xAxis.labelRotationAngle = 0f

        //Part6
        lineChart.data = LineData(vl)

        //Part7
        lineChart.axisRight.isEnabled = false
        val j = 1
        lineChart.xAxis.axisMaximum = j + 0.1f

        //Part8
        lineChart.setTouchEnabled(true)
        lineChart.setPinchZoom(true)

        //Part9
        lineChart.description.text = "Days"
        lineChart.setNoDataText("No forex yet!")

        //Part10
        lineChart.animateX(1800, Easing.EaseInExpo)
    }
}
package com.example.rda_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DetailedReportsAdapter(private val mList: List<Report>) : RecyclerView.Adapter<DetailedReportsAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.detailed_reports_layout, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]

        // sets the text to the textview from our itemHolder class
        holder.locationView.text = itemsViewModel.location
        holder.dateView.text = itemsViewModel.date
        holder.timeView.text = itemsViewModel.time
        holder.incidentDetailsView.text = itemsViewModel.incidentDetails
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val locationView: TextView = itemView.findViewById(R.id.lblLocation)
        val dateView: TextView = itemView.findViewById(R.id.lblDate)
        val timeView: TextView = itemView.findViewById(R.id.lblTime)
        val incidentDetailsView: TextView = itemView.findViewById(R.id.lblIncidentDetails)
    }
}

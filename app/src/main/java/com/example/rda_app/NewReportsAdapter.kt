package com.example.rda_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class NewReportsAdapter(private val mList: List<Report>, private val listener: OnItemClickListener) : RecyclerView.Adapter<NewReportsAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.new_reports_layout, parent, false)

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
        holder.hiddenId.text = itemsViewModel.reportId
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView), View.OnClickListener {
        val locationView: TextView = itemView.findViewById(R.id.lblLocation)
        val dateView: TextView = itemView.findViewById(R.id.lblDate)
        val timeView: TextView = itemView.findViewById(R.id.lblTime)
        val incidentDetailsView: TextView = itemView.findViewById(R.id.lblIncidentDetails)
        val hiddenId: TextView = itemView.findViewById(R.id.lblHiddenId)
        val cardView: CardView = itemView.findViewById(R.id.cardNewReport)

        // clickable buttons
        private val approveButton: Button = itemView.findViewById(R.id.btnApprove)
        private val rejectButton: Button = itemView.findViewById(R.id.btnReject)

        init {
            approveButton.setOnClickListener(this)
            rejectButton.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            // take adapter position and text
            val position: Int = adapterPosition
            val appText: String = approveButton.text as String
            val rejText: String = rejectButton.text as String
            val id: String = hiddenId.text as String

            // Different values will be passed on to the activity based on the button clicked
            if (v == approveButton) {
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position, appText, id)
                    cardView.visibility = View.INVISIBLE
                    cardView.layoutParams.height = 0
                }
            }
            else if (v == rejectButton) {
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position, rejText, id)
                    cardView.visibility = View.INVISIBLE
                    cardView.layoutParams.height = 0
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, text: String, id: String)
    }
}

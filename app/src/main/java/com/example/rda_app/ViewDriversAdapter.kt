package com.example.rda_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewDriversAdapter(private val mList: List<Drivers>) : RecyclerView.Adapter<ViewDriversAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_drivers_layout, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]

        // sets the text to the textview from our itemHolder class
        holder.usernameView.text = itemsViewModel.username
        holder.emailView.text = itemsViewModel.email
        holder.insuranceView.text = itemsViewModel.insurance
        holder.phoneView.text = itemsViewModel.phone
        holder.regView.text = itemsViewModel.reg
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val usernameView: TextView = itemView.findViewById(R.id.lblUsernameValue)
        val emailView: TextView = itemView.findViewById(R.id.lblEmailValue)
        val insuranceView: TextView = itemView.findViewById(R.id.lblInsuranceValue)
        val phoneView: TextView = itemView.findViewById(R.id.lblPhoneValue)
        val regView: TextView = itemView.findViewById(R.id.lblRegValue)
    }
}

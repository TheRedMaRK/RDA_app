package com.example.rda_app

data class Report(
    val userId: String? = null,
    val location: String? = null,
    val date: String? = null,
    val time: String? = null,
    val incidentDetails: String? = null,
    val approved: Boolean? = null,
    val reportId: String? = null
)

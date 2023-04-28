package com.example.econstat_android.Model

import java.io.Serializable

data class Insurance(
    val _id:String,
    val name: String,
    val numContrat: String,
    val agency: String,
    val validityFrom: String,
    val validityTo: String,
    val carID:String
): Serializable {}

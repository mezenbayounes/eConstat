package com.example.econstat_android.Services

import com.example.econstat_android.Model.Insurance
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface InsuranceService {
    data class InsuranceResponse(
        @SerializedName("insurance")
        val insurance: Insurance
    )
    data class InsuranceBody(

        val name: String,
        val numContrat: String,
        val agency: String,
        val validityFrom: String,
        val validityTo: String,
        val carID:String
    )

    ////////////////////////////////////////////////////////////////////////
    @POST("/insurance/addNew")
    fun addInsurance(@Body insuranceBody: InsuranceBody): Call<InsuranceResponse>


}
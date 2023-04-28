package com.example.econstat_android.ViewModel

import com.example.econstat_android.Services.ApiService
import com.example.econstat_android.Services.InsuranceService
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.econstat_android.ViewModel.MainActivity
import com.example.econstat_android.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class add_insurance : AppCompatActivity()  {
    lateinit var spinner_name: Spinner
    var name=""
    val context = this@add_insurance

    private lateinit var et_num_contrat : TextInputEditText;
    private lateinit var lyt_num_contrat : TextInputLayout;
    private lateinit var et_agency : TextInputEditText;
    private lateinit var lyt_agency : TextInputLayout;
    private lateinit var et_from : EditText;
    private lateinit var et_to : EditText;
    private lateinit var done_btn : ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_insurance)
        spinner_name =findViewById(R.id.spinner_name)
        et_num_contrat=findViewById(R.id.et_num_contrat)
        lyt_num_contrat=findViewById(R.id.lyt_num_contrat)
        et_agency=findViewById(R.id.et_num_contrat)
        lyt_agency=findViewById(R.id.lyt_num_contrat)
        et_from=findViewById<EditText>(R.id.et_from)
        et_to=findViewById<EditText>(R.id.et_to)
        done_btn=findViewById(R.id.done_btn)

        val insurances= arrayOf("star","amen","AAA","BBB")
        val adapter_insurances= ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,insurances)
        spinner_name.adapter=adapter_insurances


        spinner_name.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {


            override fun onItemSelected(parent: AdapterView<*>?,
                                        view: View?,
                                        position: Int,
                                        id:Long
            ){
                val selectedItem=insurances[position]
                Toast.makeText(this@add_insurance, "selectedItem: $selectedItem", Toast.LENGTH_SHORT).show()
                name= "$selectedItem"
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }


        })

        done_btn!!.setOnClickListener{

            if(validateInput()){
//////////////////////start





                val carID="6438f4538f51b65784a476db"
                ApiService.insuranceService.addInsurance(
                    InsuranceService.InsuranceBody(
                        name,
                        et_num_contrat.text.toString(),
                        et_agency.text.toString(),
                        et_from.text.toString(),
                        et_to.text.toString(),
                        carID

                    )
                )


                    .enqueue(
                        object : Callback<InsuranceService.InsuranceResponse> {
                            override fun onResponse(
                                call: Call<InsuranceService.InsuranceResponse>,
                                response: Response<InsuranceService.InsuranceResponse>
                            ) {
                                if (response.code() == 200) {
                                    val intent = Intent(this@add_insurance, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                    println("status code is " + response.code())

                                    showDialog(context,"Insurance Added ")


                                } else if (response.code() == 409) {
                                    showDialog(context,"Insurance Exist ")
                                } else {
                                    println("status code is " + response.code())
                                }
                            }

                            override fun onFailure(call: Call<InsuranceService.InsuranceResponse>, t: Throwable) {

                                println("HTTP ERROR")
                                t.printStackTrace()
                            }

                        })






//end if valide input
            }
        }













    }











    private fun validateInput(): Boolean {
        //Vérifier si les champs des 3 EditText ne sont pas vides
        if (setError(et_num_contrat, getString(R.string.must_not_be_empty))||setError(et_agency, getString(
                R.string.must_not_be_empty
            ))
            ) {
            Toast.makeText(this@add_insurance, R.string.must_not_be_empty, Toast.LENGTH_SHORT).show()
            return false
        } else {
            return  true

        }
    }
    private fun setError(et: TextInputEditText, errorMsg: String): Boolean {
        if(et.text?.isEmpty() == true){
            (et.parent.parent as TextInputLayout).isErrorEnabled = true
            (et.parent.parent as TextInputLayout).error = errorMsg
            return true
        }else{
            (et.parent.parent as TextInputLayout).isErrorEnabled = false
            return false
        }
    }
    fun showDialog(activityName: Context, message:String){
        val builder = AlertDialog.Builder(activityName)
        builder.setTitle("Caution ⚠️")
        builder.setMessage(message)
        builder.setPositiveButton("OK", null)
        val dialog = builder.create()
        dialog.show()
    }

/////////////////////////////////////////////
}
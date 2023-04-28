package com.example.econstat_android.ViewModel

import ViewModel.CarAdapter
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.econstat_android.Model.Car
import com.example.econstat_android.R
import com.example.econstat_android.Services.ApiService
import com.example.econstat_android.Services.CarService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeCarFragment : Fragment() {
    private lateinit var rvCars: RecyclerView
    private lateinit var carAdapter: CarAdapter
    private lateinit var add_car_btn: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val fragmentManager = requireFragmentManager()
        val rootView = inflater.inflate(R.layout.fragment_home_car, container, false)

        ////////////////////////////////////////////////
        val token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7Il9pZCI6IjY0MzVlZjcyYWVjNDQ0ZDY3YjYxNTk0NiIsIm5hbWUiOiJtZXplbiIsImxhc3ROYW1lIjoiYmF5b3VuZXMiLCJhZHJlc3MiOiJNbmlobGEiLCJkcml2ZXJMaWNlbnNlIjoiMTQ3ODUyMzY5IiwiZGVsZXZyZWRPbiI6IjIwMjAtMDMtMDlUMDA6MDA6MDAuMDAwWiIsIm51bWJlciI6MTIzNDU2NzgsInBhc3N3b3JkIjoiJDJiJDEwJGhCVmpRMElGYlhWOXNPclJKWUY5bU96NGtjRG9CQk1pYmk1bkNBd1RNSkVWUXJkaFY1T0RXIiwiZW1haWwiOiJtZXplbi5iYXlvdW5lc0Blc3ByaXQudG4iLCJ2ZXJpZmllZCI6ZmFsc2UsIm90cCI6ODk4OSwiY2FycyI6WyI2NDM2MjJmYjQ2YjViOTJlNDJlMzNkY2EiLCI2NDM3Y2I0MzRhMGIwMWQzMWIyMGRhMzkiLCI2NDM3Y2Q2ODRhMGIwMWQzMWIyMGRhM2UiLCI2NDM3Y2UwMTRhMGIwMWQzMWIyMGRhNDUiLCI2NDM3Y2U1MzRhMGIwMWQzMWIyMGRhNGEiLCI2NDM3Y2ViZDRhMGIwMWQzMWIyMGRhNGYiLCI2NDM3Y2YxMDRhMGIwMWQzMWIyMGRhNTQiLCI2NDM3ZDJlMDRhMGIwMWQzMWIyMGRhNWYiLCI2NDM4YWVjN2E2NzVhM2NmZDc1NjBiMTUiLCI2NDM4YmYwOGE2NzVhM2NmZDc1NjBiMWMiLCI2NDM4ZjA2ZDhmNTFiNjU3ODRhNDc2YjUiLCI2NDM4ZjI0MThmNTFiNjU3ODRhNDc2YzEiLCI2NDM4ZjJhYzhmNTFiNjU3ODRhNDc2YzYiLCI2NDM4ZjMwNThmNTFiNjU3ODRhNDc2Y2IiLCI2NDM4ZjM1NThmNTFiNjU3ODRhNDc2ZDAiLCI2NDM4ZjQ1MzhmNTFiNjU3ODRhNDc2ZGIiLCI2NDM4ZjU3ZjhmNTFiNjU3ODRhNDc2ZTAiLCI2NDM4Zjc4MThmNTFiNjU3ODRhNDc2ZmUiLCI2NDM5MTRlNzg3OWQwMmU2ZWQ2OWZiMmMiLCI2NDQ4MzM3MzRiNGI3OTIyZTRmN2ZjMzgiLCI2NDQ4NWM1MTRlZGYwZTRjZWEyNGE2NGQiLCI2NDQ4NWM2OTRlZGYwZTRjZWEyNGE2NTMiLCI2NDQ4NWNlMDRlZGYwZTRjZWEyNGE2NTkiLCI2NDQ4ZDM0ZWVlYzJhMGY1NzQ1ZjkyNTgiLCI2NDQ4ZDM3N2VlYzJhMGY1NzQ1ZjkyNjAiLCI2NDQ4ZDM4YWVlYzJhMGY1NzQ1ZjkyNjYiLCI2NDQ5NWRmMDdmMTZkNjMyMDgzMDYyYzMiLCI2NDQ5NjliYTdmMTZkNjMyMDgzMDYyZjYiLCI2NDQ5NjlkMjdmMTZkNjMyMDgzMDYyZmMiXSwiY3JlYXRlZEF0IjoiMjAyMy0wNC0xMVQyMzozODoyNi42MTZaIiwidXBkYXRlZEF0IjoiMjAyMy0wNC0yNlQxODoxMzozOC41NTdaIiwiX192IjoyOX0sImlhdCI6MTY4MjY3MTM3NCwiZXhwIjoxNjgyNjc0OTc0fQ.Sw5drygifRba2ItQV6LWhjGCPvaYKTQoMYTBWSrPUuk"
        println("token")
        println(token)
        ApiService.carService.getCars(
            CarService.GetAllCarsBody(
                token
            )
        ).enqueue(object : Callback<CarService.CarResponse> {

            override fun onResponse(call: Call<CarService.CarResponse>, response: Response<CarService.CarResponse>) {
                if (response.code() == 200) {
                    val cars= response.body()?.cars
                    if (cars != null) {
                        carAdapter.cars =cars // update the adapter with the retrieved cars
                        carAdapter.notifyDataSetChanged() // notify the adapter that the data has changed
                    }
                } else {
                    Log.e(TAG, "Failed to get cars: ${response.code()}")
                }            }

            override fun onFailure(call: Call<CarService.CarResponse>, t: Throwable) {
                Log.e(TAG, "Failed to get cars", t)
            }
        })

        add_car_btn = rootView.findViewById(R.id.add_car)
        rvCars = rootView.findViewById(R.id.rv)
        rvCars.layoutManager = LinearLayoutManager(requireContext())
        carAdapter = CarAdapter(listOf(),fragmentManager) // create an empty adapter
        rvCars.adapter = carAdapter




        add_car_btn.setOnClickListener {
            navigateToAddCar( fragmentManager)
        }


        return rootView





    }

    private fun navigateToAddCar(fragmentManager: FragmentManager) {

        val AddCarFragment = NewCarFragment()

        fragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, AddCarFragment)
            addToBackStack(null)
            commit()
        }
    }

    companion object {
        private const val TAG = "car"
    }
}






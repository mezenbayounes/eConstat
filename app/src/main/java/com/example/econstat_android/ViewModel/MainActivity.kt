package com.example.econstat_android.ViewModel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.econstat_android.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val homeCarFragment = HomeCarFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, homeCarFragment)
            .commit()
    }
}
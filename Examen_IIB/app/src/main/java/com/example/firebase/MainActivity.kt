package com.example.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openActivity(ListConcesionario::class.java)

    }

    private fun openActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }


}
package com.arjun.services

import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val serviceIntent: Intent by lazy { Intent(this, MyService::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_service.setOnClickListener {
            startService(serviceIntent)
        }

        stop_service.setOnClickListener {
            stopService(serviceIntent)
        }
    }
}
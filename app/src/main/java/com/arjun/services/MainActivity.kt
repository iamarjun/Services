package com.arjun.services

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val serviceIntent: Intent by lazy { Intent(this, MyService::class.java) }
    private lateinit var serviceConnection: ServiceConnection
    private var isServiceBound: Boolean = false
    private lateinit var myService: MyService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        random_number

        start_service.setOnClickListener {
            startService(serviceIntent)
        }

        stop_service.setOnClickListener {
            stopService(serviceIntent)
        }

        bind_service.setOnClickListener {
            if (!::serviceConnection.isInitialized)
                serviceConnection = object: ServiceConnection {
                    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                        myService = (service as MyService.MyServiceBinder).getService
                        isServiceBound = true
                    }

                    override fun onServiceDisconnected(name: ComponentName?) {
                        isServiceBound = false
                    }

                }

            bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE )
        }

        unbind_service.setOnClickListener {
            if (isServiceBound && ::serviceConnection.isInitialized) {
                unbindService(serviceConnection)
                isServiceBound = false
            }
        }

        get_random_number.setOnClickListener {
            if (isServiceBound)
                random_number.text = "Random No: ${myService.getRandomNumber}"
            else
                random_number.text = "Service not bound"
        }
    }
}
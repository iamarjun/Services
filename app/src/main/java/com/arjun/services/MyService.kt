package com.arjun.services

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import timber.log.Timber

class MyService : Service() {

    override fun onCreate() {
        super.onCreate()
        Timber.d("onCreate")
    }

    /**
     * Gets executed whenever we start a service
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.d("onStartCommand")
        val string = intent?.getStringExtra("input")

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivities(this, 0, arrayOf(notificationIntent), 0)
        val notification = NotificationCompat.Builder(this, MyApp.CHANNEL_ID)
            .setContentTitle("Foreground Service")
            .setContentText(string)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy")
    }

}
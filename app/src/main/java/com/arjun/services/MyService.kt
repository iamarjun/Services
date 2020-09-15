package com.arjun.services

import android.app.IntentService
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import timber.log.Timber
import kotlin.random.Random

class MyService : IntentService(MyService::class.java.canonicalName) {

    private var randomNumber: Int = 0
    private var isRandomGeneratorOn: Boolean = false

    private val MIN = 0
    private val MAX = 100

    val getRandomNumber
        get() = randomNumber

    inner class MyServiceBinder : Binder() {
        val getService: MyService
            get() = this@MyService
    }


    private val mBinder: IBinder = MyServiceBinder()


    override fun onCreate() {
        super.onCreate()
        Timber.d("onCreate")
    }

    /**
     * Gets executed whenever we start a service
     */
    override fun onHandleIntent(intent: Intent?) {
        Timber.d("onStartCommand")
        isRandomGeneratorOn = true
        startRandomNumberGenerator()
    }

    override fun onBind(intent: Intent?): IBinder? {
        Timber.d("onBind")

        return mBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Timber.d("onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopRandomNumberGenerator()
        Timber.d("onDestroy")
    }

    private fun startRandomNumberGenerator() {
        while (isRandomGeneratorOn) {
            try {
                Thread.sleep(1000)
                if (isRandomGeneratorOn) {
                    randomNumber = Random.nextInt(MAX) + MIN
                    Timber.d("${Thread.currentThread().name} Random Number: $randomNumber")
                }
            } catch (e: Exception) {
                Timber.d("Thread Interrupted")
            }

        }
    }


    private fun stopRandomNumberGenerator() {
        isRandomGeneratorOn = false
    }

}
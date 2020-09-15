package com.arjun.services

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import timber.log.Timber
import kotlin.random.Random

class MyService : JobIntentService() {

    private var randomNumber: Int = 0
    private var isRandomGeneratorOn: Boolean = false

    private val MIN = 0
    private val MAX = 100


    override fun onCreate() {
        super.onCreate()
        Timber.d("onCreate")
    }

    override fun onHandleWork(intent: Intent) {
        Timber.d("onHandleIntent")
        isRandomGeneratorOn = true
        startRandomNumberGenerator()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.d("onStartCommand")
        return super.onStartCommand(intent, flags, startId)
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
                    Timber.d("${Thread.currentThread().id} Random Number: $randomNumber")
                }
            } catch (e: Exception) {
                Timber.d("Thread Interrupted")
            }

        }
    }


    private fun stopRandomNumberGenerator() {
        isRandomGeneratorOn = false
    }

    companion object {
        const val JOB_ID = 101
        fun enqueue(context: Context, intent: Intent) {
            enqueueWork(context, MyService::class.java, JOB_ID, intent)
        }
    }

}
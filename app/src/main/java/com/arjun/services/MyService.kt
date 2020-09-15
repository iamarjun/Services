package com.arjun.services

import android.app.job.JobParameters
import android.app.job.JobService
import android.os.Handler
import android.widget.Toast
import timber.log.Timber
import java.util.*
import kotlin.random.Random

class MyService : JobService() {

    private var randomNumber: Int = 0
    private var isRandomGeneratorOn: Boolean = false

    private val MIN = 0
    private val MAX = 100
    private val handler = Handler()
    private var timer: Timer? = null

    inner class Toaster : TimerTask() {
        override fun run() {
            handler.post {
                Toast.makeText(applicationContext, "Toast", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onCreate() {
        super.onCreate()
        Timber.d("onCreate")
        timer = Timer()
    }

    /**
     * return true for long duration tasks
     * return false for short duration tasks
     *
     * runs on UI thread by default, spawn a new thread when doing some
     * long running tasks
     */
    override fun onStartJob(params: JobParameters?): Boolean {
        Timber.d("onStartJob")
        isRandomGeneratorOn = true
        timer?.scheduleAtFixedRate(Toaster(), 0, 3000)
        Thread {
            startRandomNumberGenerator(null)
        }.start()
        return true
    }

    /**
     * return true to restart when resources are available
     * return false when there is no need to restart the service
     */
    override fun onStopJob(params: JobParameters?): Boolean {
        Timber.d("onStopJob")
        timer?.cancel()
        isRandomGeneratorOn = false
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy")
    }

    private fun startRandomNumberGenerator(starter: String?) {
        while (isRandomGeneratorOn) {
            try {
                Thread.sleep(1000)
                if (isRandomGeneratorOn) {
                    randomNumber = Random.nextInt(MAX) + MIN
                    Timber.d("${Thread.currentThread().id} Random Number: $randomNumber $starter")
                }
            } catch (e: Exception) {
                Timber.d("Thread Interrupted")
            }

        }
    }

    companion object {
        var JOB_ID = 101
    }

}
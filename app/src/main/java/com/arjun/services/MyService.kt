package com.arjun.services

import android.app.IntentService
import android.app.Service
import android.content.Intent
import android.os.*
import timber.log.Timber
import kotlin.random.Random

class MyService : IntentService(MyService::class.java.canonicalName) {

    private var randomNumber: Int = 0
    private var isRandomGeneratorOn: Boolean = false

    private val MIN = 0
    private val MAX = 100

    val getRandomNumber
        get() = randomNumber

    inner class RandomNumberRequestHandler : Handler() {
        override fun handleMessage(msg: Message) {
            if (msg.what == GET_RANDOM_NUMBER_FLAG) {
                val message = Message.obtain(null, GET_RANDOM_NUMBER_FLAG)
                message.arg1 = getRandomNumber

                try {
                    msg.replyTo.send(message)
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
            super.handleMessage(msg)
        }
    }


    /**
     * Messenger is just a wrapper around Binder
     */
    private val randomNumberMessenger = Messenger(RandomNumberRequestHandler())


    override fun onCreate() {
        super.onCreate()
        Timber.d("onCreate")
    }

    override fun onHandleIntent(intent: Intent?) {
        Timber.d("onHandleIntent")
        isRandomGeneratorOn = true
        startRandomNumberGenerator()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.d("onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }
    override fun onBind(intent: Intent?): IBinder? {
        Timber.d("onBind")

        return randomNumberMessenger.binder
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
        const val GET_RANDOM_NUMBER_FLAG = 0
    }

}
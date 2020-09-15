package com.arjun.services

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val jobScheduler: JobScheduler by lazy { getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_service.setOnClickListener {
            startJob()
        }

        stop_service.setOnClickListener {
            stopJob()
        }

    }

    private fun stopJob() {
        jobScheduler.cancel(MyService.JOB_ID)
    }

    private fun startJob() {
        val componentName = ComponentName(this, MyService::class.java)
        val jobInfo = JobInfo.Builder(MyService.JOB_ID, componentName)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setPeriodic(15 * 60 * 1000)
            .setRequiresCharging(false)
            .setPersisted(true)
            .build()

        val result = jobScheduler.schedule(jobInfo)

        if (result == JobScheduler.RESULT_SUCCESS)
            Toast.makeText(this, "Successfully scheduled the job", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "Could not schedule the job", Toast.LENGTH_SHORT).show()
    }
}
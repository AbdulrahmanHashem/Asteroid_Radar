package com.example.asteroidradar

import android.app.Application
import androidx.work.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class AsteroidRadarApplication: Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()

        applicationScope.launch {
            setupUpdateDatabaseWork()
        }
    }

    private fun setupUpdateDatabaseWork() {
        val constraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<UpdateDatabaseWorker>(
            1, TimeUnit.DAYS)
            .setConstraints(constraint)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            UpdateDatabaseWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }
}
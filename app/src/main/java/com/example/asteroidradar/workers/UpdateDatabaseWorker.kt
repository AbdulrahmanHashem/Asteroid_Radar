package com.example.asteroidradar

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.asteroidradar.database.AsteroidDatabase
import com.example.asteroidradar.repository.AsteroidsRepository
import retrofit2.HttpException

class UpdateDatabaseWorker(appContext: Context, params: WorkerParameters): CoroutineWorker(appContext, params){
    companion object {
        const val WORK_NAME = "UpdateDatabaseWorker"
    }

    override suspend fun doWork(): Result {
        val database = AsteroidDatabase.getInstance(applicationContext)
        val repository = AsteroidsRepository(database)
        return try {
            repository.updateDatabase()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}
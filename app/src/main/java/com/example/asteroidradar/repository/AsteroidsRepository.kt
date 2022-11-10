package com.example.asteroidradar.repository

import android.content.Context
import androidx.room.Room
import com.example.asteroidradar.datamodels.Asteroid
import com.example.asteroidradar.api.*
import com.example.asteroidradar.database.AsteroidDatabase
import com.example.asteroidradar.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject


class AsteroidsRepository(private val asteroidDatabase: AsteroidDatabase) {

    fun getAll(): List<Asteroid> {
        return asDomainModel(asteroidDatabase.asteroidDatabaseDao.getAllAsteroids())
    }

    fun getToday(): List<Asteroid> {
        return asDomainModel(asteroidDatabase.asteroidDatabaseDao.getTodayAsteroids())
    }

    fun getWeek(): List<Asteroid> {
        return asDomainModel(asteroidDatabase.asteroidDatabaseDao.getWeekAsteroids())
    }

    suspend fun updateDatabase() {
        val asteroidsString = AsteroidRadarApi.retrofitService.getAsteroids(HashMap())
        val parsedString = parseAsteroidsJsonResult(JSONObject(asteroidsString))

        val asteroidsList = NetworkAsteroidsContainer(toDomainModel(parsedString))
        withContext(Dispatchers.IO){
            asteroidDatabase.asteroidDatabaseDao.Insert(*asteroidsList.asDatabaseModel())
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AsteroidsRepository? = null

        fun getInstance(asteroidDatabase: AsteroidDatabase) : AsteroidsRepository {
            synchronized(this){
                var instance = INSTANCE

                if (instance == null) {
                    instance = AsteroidsRepository(asteroidDatabase)
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
package com.example.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.asteroidradar.Asteroid
import com.example.asteroidradar.api.*
import com.example.asteroidradar.database.AsteroidDatabase
import com.example.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidsRepository(private val asteroidDatabase: AsteroidDatabase) {
    val Asteroids: LiveData<List<Asteroid>> = Transformations.map(asteroidDatabase.asteroidDatabaseDao.getAllAsteroids()) {
        it.asDomainModel()
    }

    suspend fun updateDatabase() {
        val asteroidsString = AsteroidRadarApi.retrofitService.getAsteroids(HashMap())
        val parsedString = parseAsteroidsJsonResult(JSONObject(asteroidsString))

        val asteroidsList = NetworkAsteroidsContainer(toDomainModel(parsedString))
        withContext(Dispatchers.IO){
            asteroidDatabase.asteroidDatabaseDao.Insert(*asteroidsList.asDatabaseModel())
        }
    }
}
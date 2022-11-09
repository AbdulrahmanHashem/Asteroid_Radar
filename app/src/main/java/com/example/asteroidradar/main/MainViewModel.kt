package com.example.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.example.asteroidradar.Asteroid
import com.example.asteroidradar.PictureOfDay
import com.example.asteroidradar.api.AsteroidImageOTDApi
import com.example.asteroidradar.api.AsteroidRadarApi
import com.example.asteroidradar.database.AsteroidDatabase
import com.example.asteroidradar.database.asDomainModel
import com.example.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {

//    private val _Asteroids = MutableLiveData<List<Asteroid>>()
//    val Asteroids: LiveData<List<Asteroid>>
//        get() = _Asteroids
//
//    private fun getAsteroids(filter: HashMap<String, String> = HashMap()) {
//        viewModelScope.launch {
//            try {
//                _status.value = AsteroidRadarApiStatus.LOADING
//                val listResult = AsteroidRadarApi.retrofitService.getAsteroids(filter)
//                _Asteroids.value = listResult
//                _status.value = AsteroidRadarApiStatus.DONE
//            } catch (e: Exception) {
//                _status.value = AsteroidRadarApiStatus.ERROR
//                _Asteroids.value = ArrayList()
//                println(e.message)
//            }
//            println(_Asteroids.value)
//        }
//    }

    private val database = AsteroidDatabase.getInstance(application)
    private val repository = AsteroidsRepository(database)

    val asteroids = repository.Asteroids

    init {
        getPictureOfDay()

        viewModelScope.launch {
            repository.updateDatabase()
        }
    }

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private fun getPictureOfDay() {
        viewModelScope.launch {
            try {
                val result = AsteroidImageOTDApi.retrofitService.getImageOfTheDay()
                if (result.mediaType == "image"){
                    _pictureOfDay.value = result
                }
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

    private val _navigateToSelectedAsteroid = MutableLiveData<Asteroid?>()
    val navigateToSelectedProperty: LiveData<Asteroid?>
        get() = _navigateToSelectedAsteroid

    fun displayAsteroidDetails(asteroid: Asteroid) {
        _navigateToSelectedAsteroid.value = asteroid
    }

    fun displayAsteroidDetailsComplete() {
        _navigateToSelectedAsteroid.value = null
    }

}
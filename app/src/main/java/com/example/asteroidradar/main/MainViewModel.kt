package com.example.asteroidradar.main

import android.app.Application
import android.view.View
import androidx.lifecycle.*
import com.example.asteroidradar.Asteroid
import com.example.asteroidradar.PictureOfDay
import com.example.asteroidradar.api.AsteroidImageOTDApi
import com.example.asteroidradar.api.AsteroidRadarApi
import com.example.asteroidradar.database.AsteroidDatabase
import com.example.asteroidradar.database.asDomainModel
import com.example.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch

enum class Status(val value: Int){ Visible(View.VISIBLE), Invisible(View.INVISIBLE) }

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status>
        get() = _status

    private val database = AsteroidDatabase.getInstance(application)
    private val repository = AsteroidsRepository(database)

    init {
        getPictureOfDay()

        viewModelScope.launch {
            try {
                _status.value = Status.Visible
                repository.updateDatabase()
                _status.value = Status.Invisible
            } catch (e: Exception) {
                println(e.message)
                _status.value = Status.Invisible
            }
        }
    }

    var asteroids = repository.Asteroids

    fun showTodayAsteroids() {
        viewModelScope.launch {
            asteroids = repository.todayAsteroids
        }
    }

    fun showWeekAsteroids() {
        viewModelScope.launch {
            asteroids = repository.Asteroids
        }
    }

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private fun getPictureOfDay() {
        viewModelScope.launch {
            try {
                _status.value = Status.Visible
                val result = AsteroidImageOTDApi.retrofitService.getImageOfTheDay()
                if (result.mediaType == "image"){
                    _pictureOfDay.value = result
                }
                _status.value = Status.Invisible
            } catch (e: Exception) {
                println(e.message)
                _status.value = Status.Invisible
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
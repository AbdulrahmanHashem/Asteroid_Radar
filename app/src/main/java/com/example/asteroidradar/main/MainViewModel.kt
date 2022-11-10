package com.example.asteroidradar.main

import android.app.Application
import android.view.View
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.asteroidradar.datamodels.Asteroid
import com.example.asteroidradar.datamodels.PictureOfDay
import com.example.asteroidradar.api.AsteroidImageOTDApi
import com.example.asteroidradar.database.AsteroidDatabase
import com.example.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


enum class Status(val value: Int){ Visible(View.VISIBLE), Invisible(View.INVISIBLE) }


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status>
        get() = _status

    private val database = AsteroidDatabase.getInstance(application)
    private val repository = AsteroidsRepository.getInstance(database)

    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    fun showAllAsteroids() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try {
                    _asteroids.postValue(repository.getAll())
                } catch (e: Exception) {
                    println(e.message)
                }
            }
        }
    }

    fun showTodayAsteroids() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try {
                    _asteroids.postValue(repository.getToday())
                } catch (e: Exception) {
                    println(e.message)
                }
            }
        }
    }

    fun showWeekAsteroids() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try {
                    _asteroids.postValue(repository.getWeek())
                } catch (e: Exception) {
                    println(e.message)
                }
            }
        }
    }

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
        showAllAsteroids()
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
package com.example.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asteroidradar.Asteroid
import com.example.asteroidradar.PictureOfDay
import com.example.asteroidradar.api.AsteroidRadarApi
import kotlinx.coroutines.launch


enum class AsteroidRadarApiStatus { LOADING, ERROR, DONE }


class MainViewModel : ViewModel() {

    private val _status = MutableLiveData<AsteroidRadarApiStatus>()
    val status: LiveData<AsteroidRadarApiStatus>
        get() = _status

    private val _Asteroids = MutableLiveData<List<Asteroid>>()
    val Asteroids: LiveData<List<Asteroid>>
        get() = _Asteroids

    private fun getAsteroids(filter: HashMap<String, String> = HashMap()) {
        viewModelScope.launch {
            try {
                _status.value = AsteroidRadarApiStatus.LOADING
                val listResult = AsteroidRadarApi.retrofitService.getAsteroids(filter)
                _Asteroids.value = listResult
                _status.value = AsteroidRadarApiStatus.DONE
            } catch (e: Exception) {
                _status.value = AsteroidRadarApiStatus.ERROR
                _Asteroids.value = ArrayList()
//                println(e.message)
            }
//            println(_Asteroids.value)
        }
    }

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private fun getPictureOfDay() {
        viewModelScope.launch {
            try {
                _status.value = AsteroidRadarApiStatus.LOADING
                val result = AsteroidRadarApi.retrofitService.getImageOfTheDay()
                if (result.mediaType == "image"){
                    _pictureOfDay.value = result
                    _status.value = AsteroidRadarApiStatus.DONE
                }
            } catch (e: Exception) {
                _status.value = AsteroidRadarApiStatus.ERROR
                println(e.message)
            }
        }
    }

    init {
        val AsteroidApiFilter = hashMapOf<String, String>("start_date" to "2020-05-05")

        getAsteroids()
        getPictureOfDay()
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
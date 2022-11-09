package com.example.asteroidradar.api

import com.example.asteroidradar.Asteroid
import com.example.asteroidradar.database.AsteroidDatabaseEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkAsteroids(
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)

// @Json(name = "near_earth_objects")
// val JasonObj: NetworkAsteroids

@JsonClass(generateAdapter = true)
data class NetworkAsteroidsContainer(val asteroids: List<NetworkAsteroids>)

fun NetworkAsteroidsContainer.asDatabaseModel(): Array<AsteroidDatabaseEntity> {
    return asteroids.map {
        AsteroidDatabaseEntity (
            id =  it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }.toTypedArray()
}

fun toDomainModel(array: ArrayList<Asteroid>): List<NetworkAsteroids> {
    return array.map {
        NetworkAsteroids(
            id =  it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}

package com.example.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.asteroidradar.Asteroid

@Entity(tableName = "asteroids_table")
data class AsteroidDatabaseEntity(
    @PrimaryKey
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)
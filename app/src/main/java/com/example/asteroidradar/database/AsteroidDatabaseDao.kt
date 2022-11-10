package com.example.asteroidradar.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDatabaseDao {
    @Query("select * from asteroids_table order by closeApproachDate ASC")
    fun getAllAsteroids(): List<AsteroidDatabaseEntity>

    @Query("select * from asteroids_table where closeApproachDate = date('now')")
    fun getTodayAsteroids(): List<AsteroidDatabaseEntity>

    @Query("select * from asteroids_table where closeApproachDate >= date('now') order by closeApproachDate ASC")
    fun getWeekAsteroids(): List<AsteroidDatabaseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun Insert(vararg asteroidDatabaseEntity: AsteroidDatabaseEntity)
}
package com.example.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDatabaseDao {
    @Query("select * from asteroids_table")
    fun getAllAsteroids(): LiveData<List<AsteroidDatabaseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun Insert(vararg asteroidDatabaseEntity: AsteroidDatabaseEntity)
}
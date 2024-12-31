package com.example.water_tracker.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.water_tracker.data.model.Water

@Dao
interface WaterDao {

    @Insert
    fun insert(water: Water)

    @Update
    fun update(water: Water)

    @Delete
    fun delete(water: Water)

    @Query("SELECT * FROM water_table")
    fun getAll(): LiveData<List<Water>>
}




package com.example.myapplication.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.repository.model.DataItem

@Dao
interface EmployeeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(data:ArrayList<DataItem>)

    @Query("SELECT * FROM employee")
    fun fetchData():List<DataItem>
}
package com.example.myapplication.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.model.db.EmployeeDao
import com.example.myapplication.repository.model.DataItem

@Database(entities = [DataItem::class], version = 1, exportSchema = false)
abstract class EmployeeDatabase:RoomDatabase() {

    abstract fun userDao(): EmployeeDao

    companion object{
        var dbInstance: EmployeeDatabase?=null
        fun getInstance(context:Context): EmployeeDatabase {
            synchronized(EmployeeDatabase::class.java){
                if(dbInstance ==null){
                    dbInstance =Room.databaseBuilder(context, EmployeeDatabase::class.java,"my_db_test").fallbackToDestructiveMigration().build()
                }
            }
            return dbInstance!!
        }

    }
}
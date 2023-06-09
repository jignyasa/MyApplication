package com.example.myapplication.repository

import com.example.myapplication.repository.model.DataItem
import com.example.myapplication.repository.model.*

interface DataRepository{
   suspend fun getData():Result<ArrayList<DataItem>>

   suspend fun insertUser(dataItem: ArrayList<DataItem>):Result<Long>

   suspend fun fetchUser():Result<ArrayList<DataItem>>
}
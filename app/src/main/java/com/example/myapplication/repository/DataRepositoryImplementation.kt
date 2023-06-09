package com.example.myapplication.repository

import com.example.myapplication.repository.db.EmployeeDatabase
import com.example.myapplication.repository.model.DataItem
import com.example.myapplication.repository.remote.RestAPi
import com.example.myapplication.repository.model.*
import javax.inject.Inject

class DataRepositoryImplementation @Inject constructor(val api: RestAPi, val db:EmployeeDatabase) : DataRepository {
    override suspend fun getData(): Result<ArrayList<DataItem>> {
        return try {
            val response = api.getData()
            val result = response.body()
            if (response.isSuccessful) {
                var list=ArrayList<DataItem>()
                result?.data?.let {
                    list.addAll(result.data)
                }
                return Result.Success(list)
            } else {
                return Result.Error("there is no data")
            }
        } catch (e: Exception) {
            return Result.Error(e.message.toString())
        }
    }

    override suspend fun insertUser(data:ArrayList<DataItem>): Result<Long> {
        return try {
            val response = db.userDao().insertData(data)
            val result = response
            return Result.Error(response.toString())
        } catch (e: Exception) {
            return Result.Error(e.message.toString())
        }
    }

    override suspend fun fetchUser(): Result<ArrayList<DataItem>> {
        return try {
            val response = db.userDao().fetchData()
            if (response.isEmpty().not()) {
                var list=ArrayList<DataItem>()
                response?.let {
                    list.addAll(response)
                }
                return Result.Success(list)
            } else {
                return Result.Error("there is no data")
            }
        } catch (e: Exception) {
            return Result.Error(e.message.toString())
        }
    }
}
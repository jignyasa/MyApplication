package com.example.myapplication.repository.remote

import com.example.myapplication.repository.model.ResponseData
import retrofit2.Response
import retrofit2.http.GET

interface RestAPi {
    @GET("employees")
    suspend fun getData(): Response<ResponseData>
}
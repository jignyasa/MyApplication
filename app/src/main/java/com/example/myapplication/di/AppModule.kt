package com.example.myapplication.di

import com.example.myapplication.BaseApplication
import com.example.myapplication.repository.DataRepository
import com.example.myapplication.repository.DataRepositoryImplementation
import com.example.myapplication.repository.db.EmployeeDatabase
import com.example.myapplication.repository.remote.RestAPi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun getBaseUrl() = "https://dummy.restapiexample.com/api/v1/"

    @Provides
    @Singleton
    fun okHttpClient() = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @Provides
    @Singleton
    fun retrofit() = Retrofit.Builder().baseUrl(getBaseUrl()).client(okHttpClient())
        .addConverterFactory(GsonConverterFactory.create()).build()

    @Provides
    @Singleton
    fun myApi()=retrofit().create(RestAPi::class.java)

    @Provides
    @Singleton
    fun applicationContext()=BaseApplication.mContext

    @Provides
    @Singleton
    fun myDb()=EmployeeDatabase.getInstance(applicationContext())

    @Provides
    @Singleton
    fun repository():DataRepository{
      return DataRepositoryImplementation(myApi(),myDb())
    }
}
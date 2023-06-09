package com.example.myapplication

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {
    //private lateinit var mContext: Context

    override fun onCreate() {
        super.onCreate()
        mContext=applicationContext
    }
    companion object{
        lateinit var mContext: Context
    }
}
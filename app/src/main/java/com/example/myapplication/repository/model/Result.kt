package com.example.myapplication.repository.model

sealed class Result<T>(val data:T?,val message:String?) {
    class Success<T>(data: T) : Result<T>(data,null){}
    class Error<T>(message: String?) : Result<T>(null,message){}
}
package com.example.myapplication.repository.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseData(@Expose @SerializedName("data")
                        val data: List<DataItem>?,
                        @Expose @SerializedName("message")
                        val message: String? = "",
                        @Expose @SerializedName("status")
                        val status: String? = "")
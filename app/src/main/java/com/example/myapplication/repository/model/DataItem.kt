package com.example.myapplication.repository.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
@Entity(tableName = "employee")
data class DataItem(@Expose @SerializedName("profile_image")
                    val profileImage: String? = "",
                    @Expose @SerializedName("employee_name")
                    val employeeName: String? = "",
                    @Expose @SerializedName("employee_salary")
                    val employeeSalary: Long? = 0L,
                    @PrimaryKey
                    @Expose @SerializedName("id")
                    val id: Int? = 0,
                    @Expose @SerializedName("employee_age")
                    val employeeAge: Int? = 0)

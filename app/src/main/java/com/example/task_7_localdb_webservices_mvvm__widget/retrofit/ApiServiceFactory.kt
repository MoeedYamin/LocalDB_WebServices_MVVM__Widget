package com.example.task_7_localdb_webservices_mvvm__widget.retrofit

import com.example.task_7_localdb_webservices_mvvm__widget.ProjectConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiServiceFactory {
    private val retrofit = Retrofit.Builder()
        .baseUrl(ProjectConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
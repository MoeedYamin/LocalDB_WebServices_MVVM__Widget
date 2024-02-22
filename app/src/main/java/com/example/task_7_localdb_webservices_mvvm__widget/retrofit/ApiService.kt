package com.example.task_7_localdb_webservices_mvvm__widget.retrofit

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): Response<UsersResponse>
}

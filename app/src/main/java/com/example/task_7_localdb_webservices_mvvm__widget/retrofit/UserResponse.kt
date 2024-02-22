package com.example.task_7_localdb_webservices_mvvm__widget.retrofit

import com.example.task_7_localdb_webservices_mvvm__widget.roomDb.User
import com.google.gson.annotations.SerializedName


data class UsersResponse(
    @SerializedName("data")
    val users: List<User>
)
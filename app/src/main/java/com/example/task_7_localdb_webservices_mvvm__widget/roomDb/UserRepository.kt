package com.example.task_7_localdb_webservices_mvvm__widget.roomDb

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.task_7_localdb_webservices_mvvm__widget.retrofit.ApiService

class UserRepository(private val apiService: ApiService, private val userDao: UserDao) {
//    val users: LiveData<List<User>> = userDao.getAllUsers() // LiveData exposed from DAO

    suspend fun fetchAndStoreUsers() {
        try {
            val response = apiService.getUsers()
            if (response.isSuccessful) {
                response.body()?.users?.forEach { user ->
                    userDao.insert(user)
                    Log.d("UserRepository", "Inserted user: ${user.firstName} ${user.lastName}")
                }
            } else {
                Log.e("UserRepository", "Error fetching users: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Error fetching users", e)
        }
    }
    suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }
}
package com.example.task_7_localdb_webservices_mvvm__widget.recyclerView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task_7_localdb_webservices_mvvm__widget.roomDb.User
import com.example.task_7_localdb_webservices_mvvm__widget.roomDb.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val usersLiveData: MutableLiveData<List<User>> = MutableLiveData()

    suspend fun fetchUsers() {
        viewModelScope.launch {
            try {
                val users = userRepository.getAllUsers()
                usersLiveData.postValue(users)
            } catch (e: Exception) {
                // Handle exceptions
            }
        }
         userRepository.fetchAndStoreUsers()

    }

    fun getUsersLiveData(): LiveData<List<User>> {
        return usersLiveData
    }


}
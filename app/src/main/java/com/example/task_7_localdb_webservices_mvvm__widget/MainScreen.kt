package com.example.task_7_localdb_webservices_mvvm__widget

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.task_7_localdb_webservices_mvvm__widget.databinding.ActivityMainScreenBinding
import com.example.task_7_localdb_webservices_mvvm__widget.recyclerView.UserAdapter
import com.example.task_7_localdb_webservices_mvvm__widget.recyclerView.UserViewModel
import com.example.task_7_localdb_webservices_mvvm__widget.recyclerView.UserViewModelFactory
import com.example.task_7_localdb_webservices_mvvm__widget.retrofit.ApiService
import com.example.task_7_localdb_webservices_mvvm__widget.retrofit.ApiServiceFactory
import com.example.task_7_localdb_webservices_mvvm__widget.roomDb.AppDatabase
import com.example.task_7_localdb_webservices_mvvm__widget.roomDb.UserRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainScreen : AppCompatActivity() {
    private lateinit var binding:ActivityMainScreenBinding
    private lateinit var userRepository: UserRepository
    private lateinit var adapter: UserAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var textNoDataFound: TextView
    private lateinit var userViewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializingBinding()
        initializingVariables()
        initializingViewModel()
        setObservers()
        fetchDataFromApiAndStoreInDB()
        recyclerViewInitializer()
    }

    private fun initializingViewModel() {
        val userRepository = UserRepository(ApiServiceFactory.apiService, BaseApplication.database.userDao())
        userViewModel = ViewModelProvider(this, UserViewModelFactory(userRepository))[UserViewModel::class.java]
    }

    private fun setObservers() {
        progressBar.visibility = View.VISIBLE
        userViewModel.getUsersLiveData().observe(this) { users ->
            if (users.isNotEmpty()) {
                adapter.setUsers(users)
                textNoDataFound.visibility = View.GONE
            } else {
                adapter.setUsers(emptyList())
                textNoDataFound.visibility = View.VISIBLE
            }
            progressBar.visibility = View.GONE
        }
    }

    private fun initializingVariables() {
        recyclerView = binding.recyclerView
        progressBar = binding.progressBar
        textNoDataFound = binding.textNoDataFound
    }

    private fun recyclerViewInitializer() {
        adapter = UserAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        setObservers()
    }


    private fun fetchDataFromApiAndStoreInDB() {
        userRepository = UserRepository(ApiServiceFactory.apiService, BaseApplication.database.userDao())
        progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                userViewModel.fetchUsers()
            } catch (e: Exception) {
                progressBar.visibility = View.GONE
            }

            refreshUI()
        }
    }

    private fun refreshUI() {
        lifecycleScope.launch(Dispatchers.Main) {
            val users = userRepository.getAllUsers()

            if (users.isNotEmpty()) {
                adapter.setUsers(users)
                textNoDataFound.visibility = View.GONE
            } else {
                adapter.setUsers(emptyList())
                textNoDataFound.visibility = View.VISIBLE
            }

            progressBar.visibility = View.GONE
        }
    }

    private fun initializingBinding() {
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}

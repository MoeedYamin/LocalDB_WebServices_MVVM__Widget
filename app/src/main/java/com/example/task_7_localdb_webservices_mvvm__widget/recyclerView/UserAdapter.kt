package com.example.task_7_localdb_webservices_mvvm__widget.recyclerView

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task_7_localdb_webservices_mvvm__widget.databinding.ListItemsUserBinding
import com.example.task_7_localdb_webservices_mvvm__widget.roomDb.User

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private var users: List<User> = emptyList()

    inner class UserViewHolder(private val binding: ListItemsUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            val fullName = "${user.firstName} ${user.lastName}"
            binding.user = user
            binding.textViewFullName.text = fullName
            binding.executePendingBindings()


            Glide.with(binding.root)
                .load(user.avatar)
                .into(binding.imageViewAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ListItemsUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun setUsers(users: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }
}

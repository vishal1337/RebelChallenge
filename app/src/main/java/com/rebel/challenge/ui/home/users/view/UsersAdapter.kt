package com.rebel.challenge.ui.home.users.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rebel.challenge.data.model.User
import com.rebel.challenge.databinding.ItemUserBinding
import com.rebel.challenge.ui.home.users.UsersViewModel

class UsersAdapter(
        private var users: List<User>,
        private val usersViewModel: UsersViewModel
) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    fun replaceData(users: List<User>) {
        setList(users)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false)

        return ViewHolder(binding.root)

    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val userActionsListener = object : UserItemUserActionsListener {

            override fun onUserFavoriteClicked(user: User, v: View) {
                usersViewModel.favoriteUser(user)
                notifyDataSetChanged()
            }

            override fun onUserClicked(user: User) {
                usersViewModel.openUserEvent.value = user
            }

        }

        holder.binding?.let {
            with(it) {
                user = users[position]
                listener = userActionsListener
                executePendingBindings()
            }
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var binding: ItemUserBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)
        }
    }

    private fun setList(users: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }

}

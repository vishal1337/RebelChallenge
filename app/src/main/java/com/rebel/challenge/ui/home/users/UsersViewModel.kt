package com.rebel.challenge.ui.home.users

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableList
import androidx.lifecycle.AndroidViewModel
import com.rebel.challenge.SingleLiveEvent
import com.rebel.challenge.data.model.User
import com.rebel.challenge.data.source.UsersDataSource
import com.rebel.challenge.data.source.UsersRepository

class UsersViewModel(
    context: Application,
    private val usersRepository: UsersRepository
) : AndroidViewModel(context), IUsersViewModel {

    val openUserEvent = SingleLiveEvent<User>()

    val dataLoading = ObservableBoolean(false)

    val empty = ObservableBoolean(false)
    val snackbarMessage = SingleLiveEvent<Int>()

    // These observable fields will update Views automatically
    val items: ObservableList<User> = ObservableArrayList()

    fun start() {
        loadUsers(false)
    }

    override fun loadUsers(forceUpdate: Boolean) {
        loadUsers(forceUpdate, true)
    }

    override fun favoriteUser(user: User) {
        usersRepository.favoriteUser(
            user.apply {
                isFavorite = isFavorite.not()
            }
        )
    }

    private fun loadUsers(forceUpdate: Boolean, showLoadingUI: Boolean) {

        //Show Loader if Data is being Loaded
        if (showLoadingUI) dataLoading.set(true)

        //Clear Cache and fetch new Users
        if (forceUpdate) usersRepository.refreshUsers()

        usersRepository.getUsers(object : UsersDataSource.LoadUsersCallback {

            override fun onUsersLoaded(users: List<User>) {

                if (showLoadingUI) {
                    dataLoading.set(false)
                }

                with(items) {
                    clear()
                    addAll(users)
                    empty.set(isEmpty())
                }
            }

            override fun onDataNotAvailable() {

                if (showLoadingUI) {
                    dataLoading.set(false)
                }
            }
        })

    }

}
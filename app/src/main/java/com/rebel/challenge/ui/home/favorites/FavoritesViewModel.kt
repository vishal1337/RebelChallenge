package com.rebel.challenge.ui.home.favorites

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableList
import androidx.lifecycle.AndroidViewModel
import com.rebel.challenge.SingleLiveEvent
import com.rebel.challenge.data.model.User
import com.rebel.challenge.data.source.UsersDataSource
import com.rebel.challenge.data.source.UsersRepository

class FavoritesViewModel(
    context: Application,
    private val usersRepository: UsersRepository
) : AndroidViewModel(context), IFavoritesViewModel {

    val openUserEvent = SingleLiveEvent<User>()

    // These observable fields will update Views automatically
    val items: ObservableList<User> = ObservableArrayList()

    val empty = ObservableBoolean(false)
    val dataLoading = ObservableBoolean(false)
    val snackbarMessage = SingleLiveEvent<Int>()

    fun start() {
        loadFavoriteUsers(false)
    }

    override fun unFavoriteUser(user: User) {
        usersRepository.favoriteUser(
            user.apply {
                isFavorite = isFavorite.not()
            }
        )
    }

    override fun loadFavoriteUsers(forceUpdate: Boolean) {
        loadFavoriteUsers(forceUpdate, true)
    }

    private fun loadFavoriteUsers(forceUpdate: Boolean, showLoadingUI: Boolean) {

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
                    addAll(users.filter { it.isFavorite })
                    empty.set(isEmpty())
                }
            }

            override fun onDataNotAvailable() {

                if (showLoadingUI) {
                    dataLoading.set(false)
                }

                empty.set(true)

            }
        })

    }

}
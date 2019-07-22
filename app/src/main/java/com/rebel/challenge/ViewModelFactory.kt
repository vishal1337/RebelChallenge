package com.rebel.challenge

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rebel.challenge.data.source.UsersRepository
import com.rebel.challenge.mock.data.Injection
import com.rebel.challenge.ui.home.favorites.FavoritesViewModel
import com.rebel.challenge.ui.home.users.UsersViewModel

/**
 * A creator is used to inject the product ID into the ViewModel
 *
 *
 * This creator is to showcase how to inject dependencies into ViewModels. It's not
 * actually necessary in this case, as the product ID can be passed in a public method.
 */


class ViewModelFactory private constructor(
    private val application: Application,
    private val usersRepository: UsersRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(UsersViewModel::class.java) ->
                    UsersViewModel(application, usersRepository)

                isAssignableFrom(FavoritesViewModel::class.java) ->
                    FavoritesViewModel(application, usersRepository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application) =
            INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                INSTANCE ?: ViewModelFactory(
                    application,
                    Injection.provideUsersRepository(application.applicationContext)
                )
                    .also { INSTANCE = it }
            }

        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}

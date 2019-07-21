package com.rebel.challenge.mock.data

import android.content.Context
import com.rebel.challenge.data.source.UsersRepository
import com.rebel.challenge.data.source.local.users.UsersDatabase
import com.rebel.challenge.data.source.local.users.UsersLocalDataSource
import com.rebel.challenge.data.source.remote.users.UsersRemoteDataSource
import com.rebel.challenge.util.AppExecutors

/**
 * Enables injection of implementations for
 * [UsersLocalDataSource] at compile time. This is useful for testing, since it allows us to use
 * a fake instance of the class to isolate the dependencies and run a test hermetically.
 */
object Injection {

    fun provideUsersRepository(context: Context): UsersRepository {
        val database = UsersDatabase.getInstance(context)
        return UsersRepository.getInstance(
            UsersRemoteDataSource,
            UsersLocalDataSource.getInstance(AppExecutors(), database.userDao())
        )
    }

}
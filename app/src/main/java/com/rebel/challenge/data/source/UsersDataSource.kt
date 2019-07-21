package com.rebel.challenge.data.source

import com.rebel.challenge.data.model.User

/**
 * Main entry point for accessing users data.
 *
 *
 * For simplicity, only getUsers() and getUser() have callbacks. Additional callbacks can be added to other
 * methods to inform the user of network/database errors or successful operations.
 * For example, when a new user is created, it's synchronously stored in cache but usually every
 * operation on database or network should be executed in a different thread.
 */

interface UsersDataSource {

    interface LoadUsersCallback {

        fun onUsersLoaded(users: List<User>)

        fun onDataNotAvailable()

    }

    interface GetUserCallback {

        fun onUserLoaded(user: User)

        fun onDataNotAvailable()

    }

    fun getUsers(callback: LoadUsersCallback)

    fun saveUser(user: User)

    fun getUser(userId: String, callback: GetUserCallback)

    fun favoriteUser(user: User)

    fun deleteUser(userId: String)

    fun refreshUsers()

    fun deleteAllUsers()
}

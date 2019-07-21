package com.rebel.challenge.data.source.local.users

import androidx.annotation.VisibleForTesting
import com.rebel.challenge.data.model.User
import com.rebel.challenge.data.source.UsersDataSource
import com.rebel.challenge.util.AppExecutors

/**
 * Concrete implementation of a data source as a db.
 */
class UsersLocalDataSource private constructor(
    private val appExecutors: AppExecutors,
    private val usersDao: UsersDao
) : UsersDataSource {

    /**
     * Note: [UsersDataSource.LoadUsersCallback.onDataNotAvailable] is fired if the database doesn't exist
     * or the table is empty.
     */
    override fun getUsers(callback: UsersDataSource.LoadUsersCallback) {
        appExecutors.diskIO.execute {
            val users = usersDao.getUsers()
            appExecutors.mainThread.execute {
                users.takeIf { it.isNotEmpty() }?.let {
                    // This will be called if the table is new or just empty.
                    callback.onUsersLoaded(it)
                } ?: callback.onDataNotAvailable()
            }
        }
    }

    /**
     * Note: [UsersDataSource.GetUserCallback.onDataNotAvailable] is fired if the [User] isn't
     * found.
     */
    override fun getUser(userId: String, callback: UsersDataSource.GetUserCallback) {
        appExecutors.diskIO.execute {
            val user = usersDao.getUserById(userId)
            appExecutors.mainThread.execute {
                user?.let { callback.onUserLoaded(it) }
                    ?: callback.onDataNotAvailable()
            }
        }
    }

    override fun favoriteUser(user: User) {
        appExecutors.diskIO.execute { usersDao.favoriteUser(user.id, user.isFavorite) }
    }

    override fun saveUser(user: User) {
        appExecutors.diskIO.execute { usersDao.insertUser(user) }
    }

    override fun deleteAllUsers() {
        appExecutors.diskIO.execute { usersDao.deleteUsers() }
    }

    override fun deleteUser(userId: String) {
        appExecutors.diskIO.execute { usersDao.deleteUserById(userId) }
    }

    override fun refreshUsers() {
        // Not required because the {@link UsersRepository} handles the logic of refreshing the
        // users from all the available data sources.
    }

    companion object {
        private var INSTANCE: UsersLocalDataSource? = null

        @JvmStatic
        fun getInstance(appExecutors: AppExecutors, usersDao: UsersDao): UsersLocalDataSource {
            if (INSTANCE == null) {
                synchronized(UsersLocalDataSource::javaClass) {
                    INSTANCE = UsersLocalDataSource(appExecutors, usersDao)
                }
            }
            return INSTANCE!!
        }

        @VisibleForTesting
        fun clearInstance() {
            INSTANCE = null
        }
    }

}
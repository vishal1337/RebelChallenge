package com.rebel.challenge.data.source

import com.rebel.challenge.data.model.User
import java.util.*
import kotlin.collections.LinkedHashMap
import kotlin.collections.set

/**
 * Concrete implementation to load users from the data sources into a cache.
 *
 *
 * For simplicity, this implements a dumb synchronisation between locally persisted data and data
 * obtained from the server, by using the remote data source only if the local database doesn't
 * exist or is empty.
 */
class UsersRepository(
    private val usersRemoteDataSource: UsersDataSource,
    private val usersLocalDataSource: UsersDataSource
) : UsersDataSource {

    /**
     * This variable has public visibility so it can be accessed from tests.
     */
    var cachedUsers: LinkedHashMap<String, User> = LinkedHashMap()

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    private var cacheIsDirty = true //Set to true if you don't wanna use Cache else set to False to use LocalDB

    /**
     * Gets users from cache, local data source (SQLite) or remote data source, whichever is
     * available first.
     *
     *
     * Note: [UsersDataSource.LoadUsersCallback.onDataNotAvailable] is fired if all data sources fail to
     * get the data.
     */
    override fun getUsers(callback: UsersDataSource.LoadUsersCallback) {
        // Respond immediately with cache if available and not dirty
        if (cachedUsers.isNotEmpty() && !cacheIsDirty) {
            callback.onUsersLoaded(ArrayList(cachedUsers.values))
            return
        }

        if (cacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            getUsersFromRemoteDataSource(callback)
        } else {
            // Query the local storage if available. If not, query the network.
            usersLocalDataSource.getUsers(object : UsersDataSource.LoadUsersCallback {
                override fun onUsersLoaded(users: List<User>) {
                    refreshUserCache(users)
                    callback.onUsersLoaded(ArrayList(cachedUsers.values))
                }

                override fun onDataNotAvailable() {
                    getUsersFromRemoteDataSource(callback)
                }
            })
        }
    }

    override fun saveUser(user: User) {
        // Do in memory cache update to keep the app UI up to date
        cacheAndPerform(user) {
            usersRemoteDataSource.saveUser(it)
            usersLocalDataSource.saveUser(it)
        }
    }

    /**
     * Gets users from local data source (sqlite) unless the table is new or empty. In that case it
     * uses the network data source. This is done to simplify the sample.
     *
     *
     * Note: [UsersDataSource.GetUserCallback.onDataNotAvailable] is fired if both data sources fail to
     * get the data.
     */
    override fun getUser(userId: String, callback: UsersDataSource.GetUserCallback) {
        val userInCache = getUserWithId(userId)

        // Respond immediately with cache if available
        if (userInCache != null) {
            callback.onUserLoaded(userInCache)
            return
        }

        // Load from server/persisted if needed.

        // Is the user in the local data source? If not, query the network.
        usersLocalDataSource.getUser(userId, object : UsersDataSource.GetUserCallback {
            override fun onUserLoaded(user: User) {
                // Do in memory cache update to keep the app UI up to date
                cacheAndPerform(user) {
                    callback.onUserLoaded(it)
                }
            }

            override fun onDataNotAvailable() {
                usersRemoteDataSource.getUser(userId, object : UsersDataSource.GetUserCallback {
                    override fun onUserLoaded(user: User) {
                        // Do in memory cache update to keep the app UI up to date
                        cacheAndPerform(user) {
                            callback.onUserLoaded(it)
                        }
                    }

                    override fun onDataNotAvailable() {
                        callback.onDataNotAvailable()
                    }
                })
            }
        })
    }

    override fun favoriteUser(user: User) {
        // Do in memory cache update to keep the app UI up to date
        cacheAndPerform(user) {
            usersRemoteDataSource.favoriteUser(it)
            usersLocalDataSource.favoriteUser(it)
        }
    }

    override fun refreshUsers() {
        cacheIsDirty = true
    }

    override fun deleteAllUsers() {
        usersRemoteDataSource.deleteAllUsers()
        usersLocalDataSource.deleteAllUsers()
        cachedUsers.clear()
    }

    override fun deleteUser(userId: String) {
        usersRemoteDataSource.deleteUser(userId)
        usersLocalDataSource.deleteUser(userId)
        cachedUsers.remove(userId)
    }


    private fun getUsersFromRemoteDataSource(callback: UsersDataSource.LoadUsersCallback) {
        usersRemoteDataSource.getUsers(object : UsersDataSource.LoadUsersCallback {
            override fun onUsersLoaded(users: List<User>) {
                refreshUserCache(users)
                refreshLocalDataSource(users)
                callback.onUsersLoaded(ArrayList(cachedUsers.values))
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    private fun refreshUserCache(users: List<User>) {
        cachedUsers.clear()
        users.forEach {
            cacheAndPerform(it) {}
        }
        cacheIsDirty = false
    }

    private fun refreshLocalDataSource(users: List<User>) {
        usersLocalDataSource.deleteAllUsers()
        for (user in users) {
            usersLocalDataSource.saveUser(user)
        }
    }

    private fun getUserWithId(id: String) = cachedUsers[id]

    private inline fun cacheAndPerform(user: User, perform: (User) -> Unit) {
        val cachedUser = User(
            name = user.name,
            username = user.username,
            email = user.email,
            //address = user.address,
            phone = user.phone,
            website = user.website,
            isFavorite = user.isFavorite,
            //company = user.company,
            userId = user.userId
        )

        cachedUsers[cachedUser.userId] = cachedUser
        perform(cachedUser)
    }

    companion object {

        private var INSTANCE: UsersRepository? = null

        /**
         * Returns the single instance of this class, creating it if necessary.

         * @param usersRemoteDataSource the backend data source
         * *
         * @param usersLocalDataSource  the device storage data source
         * *
         * @return the [UsersRepository] instance
         */
        @JvmStatic
        fun getInstance(
            usersRemoteDataSource: UsersDataSource,
            usersLocalDataSource: UsersDataSource
        ) =
            INSTANCE ?: synchronized(UsersRepository::class.java) {
                INSTANCE ?: UsersRepository(usersRemoteDataSource, usersLocalDataSource)
                    .also { INSTANCE = it }
            }


        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}

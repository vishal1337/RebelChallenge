package com.rebel.challenge.data.source.remote.users

import com.rebel.challenge.RebelServiceFactory
import com.rebel.challenge.data.model.User
import com.rebel.challenge.data.source.UsersDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UsersRemoteDataSource : UsersDataSource {

    private val userService = RebelServiceFactory.makeUserService()

    override fun getUsers(callback: UsersDataSource.LoadUsersCallback) {
        userService.getUsers().enqueue(object : Callback<List<User>> {

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                response.body()?.let { callback.onUsersLoaded(it) }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                callback.onDataNotAvailable()
            }

        })
    }

    override fun saveUser(user: User) {}

    override fun getUser(userId: String, callback: UsersDataSource.GetUserCallback) {}

    override fun favoriteUser(user: User) {
        //Not Applicable
    }

    override fun deleteUser(userId: String) {}

    override fun refreshUsers() {}

    override fun deleteAllUsers() {}

}
package com.rebel.challenge.data.source.remote.users

import com.rebel.challenge.data.model.User
import retrofit2.Call
import retrofit2.http.GET

/**
 * Defines the abstract methods used for interacting with the Users API
 */

interface UserService {

    @GET("/users")
    fun getUsers(): Call<List<User>>

}
package com.rebel.challenge.ui.home.users

import com.rebel.challenge.data.model.User

interface IUsersViewModel {

    fun favoriteUser(user: User)

    fun loadUsers(forceUpdate: Boolean)

}
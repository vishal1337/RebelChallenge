package com.rebel.challenge.ui.home.favorites

import com.rebel.challenge.data.model.User

interface IFavoritesViewModel {

    fun unFavoriteUser(user: User)

    fun loadFavoriteUsers(forceUpdate: Boolean)


}
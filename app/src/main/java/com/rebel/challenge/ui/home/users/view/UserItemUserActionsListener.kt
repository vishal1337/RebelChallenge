package com.rebel.challenge.ui.home.users.view

import android.view.View
import com.rebel.challenge.data.model.User

/**
 * Listener used with data binding to process user actions.
 */
interface UserItemUserActionsListener {

    fun onUserFavoriteClicked(user: User, v: View)

    fun onUserClicked(user: User)

}
package com.rebel.challenge.ui.home.users.view

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rebel.challenge.data.model.User

/**
 * Contains [BindingAdapter]s for the [User] list.
 */
object UsersListBindings {

    @BindingAdapter("app:items")
    @JvmStatic
    fun setItems(recyclerView: RecyclerView, items: List<User>) {
        with(recyclerView.adapter as UsersAdapter) {
            replaceData(items)
        }
    }
}

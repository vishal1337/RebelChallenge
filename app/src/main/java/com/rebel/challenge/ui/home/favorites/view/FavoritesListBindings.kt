package com.rebel.challenge.ui.home.favorites.view

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rebel.challenge.data.model.User

/**
 * Contains [BindingAdapter]s for the favorite [User] list.
 */
object FavoritesListBindings {

    @BindingAdapter("app:favorites")
    @JvmStatic
    fun setItems(recyclerView: RecyclerView, items: List<User>) {
        with(recyclerView.adapter as FavoritesAdapter) {
            replaceData(items.filter { it.isFavorite })
        }
    }
}

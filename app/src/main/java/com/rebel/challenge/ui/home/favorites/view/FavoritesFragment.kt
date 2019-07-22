package com.rebel.challenge.ui.home.favorites.view


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.rebel.challenge.databinding.FragmentFavoritesBinding
import com.rebel.challenge.ui.home.HomeActivity
import com.rebel.challenge.util.setupSnackbar
import java.util.*

class FavoritesFragment : Fragment() {

    private lateinit var mContext: Context

    private lateinit var viewDataBinding: FragmentFavoritesBinding

    private lateinit var listAdapter: FavoritesAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.let { mContext = it }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentFavoritesBinding.inflate(inflater, container, false).apply {
            viewmodel = (activity as HomeActivity).obtainFavoritesViewModel()
        }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.viewmodel?.let {
            view?.setupSnackbar(this, it.snackbarMessage, Snackbar.LENGTH_LONG)
        }
        setupListAdapter()

        //Fetch Data Once Activity is Attached
        viewDataBinding.viewmodel?.start()
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            listAdapter = FavoritesAdapter(ArrayList(0), viewModel)

            viewDataBinding.favoriteUsersList.layoutManager = LinearLayoutManager(
                    context,
                    RecyclerView.VERTICAL, false
            )
            viewDataBinding.favoriteUsersList.adapter = listAdapter

        } else {
            Log.w("FavoritesFragment", "ViewModel not initialized when attempting to set up adapter.")
        }
    }

    companion object {
        fun newInstance() = FavoritesFragment()
        private const val TAG = "FavoritesFragment"

    }

}

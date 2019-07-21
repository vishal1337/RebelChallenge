package com.rebel.challenge.ui.home.users.view

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
import com.rebel.challenge.databinding.FragmentUserBinding
import com.rebel.challenge.ui.home.HomeActivity
import com.rebel.challenge.util.setupSnackbar
import java.util.*

class UsersFragment : Fragment() {

    private lateinit var mContext: Context

    private lateinit var viewDataBinding: FragmentUserBinding

    private lateinit var listAdapter: UsersAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.let { mContext = it }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentUserBinding.inflate(inflater, container, false).apply {
            viewmodel = (activity as HomeActivity).obtainUsersViewModel()
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
            listAdapter = UsersAdapter(ArrayList(0), viewModel)

            viewDataBinding.usersList.layoutManager = LinearLayoutManager(
                    context,
                    RecyclerView.VERTICAL, false
            )
            viewDataBinding.usersList.adapter = listAdapter

        } else {
            Log.w("UsersFragment", "ViewModel not initialized when attempting to set up adapter.")
        }
    }

    companion object {
        fun newInstance() = UsersFragment()
        private const val TAG = "UsersFragment"

    }

}
package com.rebel.challenge.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.rebel.challenge.R
import com.rebel.challenge.data.model.User
import com.rebel.challenge.ui.home.users.UsersViewModel
import com.rebel.challenge.util.obtainViewModel
import com.rebel.challenge.util.setupActionBar
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var usersViewModel: UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupActionBar(R.id.toolbar) {}

        setupBottomNavigation()

        usersViewModel = obtainUsersViewModel().apply {

            /*openUserEvent.observe(this@HomeActivity, Observer { user ->
                user?.let { openUserDetails(it) }
            })*/

        }
    }

    private fun setupBottomNavigation() {
        val navController = findNavController(R.id.mainNavigationHostFragment)
        bottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController)
    }

    /*
    Changes Toolbar title when Navigating among Home Navigation
     */
    private fun setupActionBarWithNavController(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ -> toolbar.title = destination.label }
    }

    private fun openUserDetails(user: User) {

    }

    fun obtainUsersViewModel(): UsersViewModel = obtainViewModel(UsersViewModel::class.java)

}

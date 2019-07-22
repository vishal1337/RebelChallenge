package com.rebel.challenge.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.maps.model.LatLng
import com.rebel.challenge.R
import com.rebel.challenge.data.model.User
import com.rebel.challenge.ui.home.favorites.FavoritesViewModel
import com.rebel.challenge.ui.home.users.UsersViewModel
import com.rebel.challenge.ui.userdetails.UserLocationActivity
import com.rebel.challenge.util.obtainViewModel
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var usersViewModel: UsersViewModel
    private lateinit var favoritesViewModel: FavoritesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupBottomNavigation()

        usersViewModel = obtainUsersViewModel().apply {
            openUserEvent.observe(this@HomeActivity, Observer { user ->
                user?.let { this@HomeActivity.openUserDetails(it) }
            })
        }

        favoritesViewModel = obtainFavoritesViewModel().apply {
            openUserEvent.observe(this@HomeActivity, Observer { user ->
                user?.let { openUserDetails(it) }
            })
        }
    }

    private fun setupBottomNavigation() {
        val navController = findNavController(R.id.mainNavigationHostFragment)
        bottomNavigationView.setupWithNavController(navController)
        //setupActionBarWithNavController(navController)
    }

    /*
    Changes Toolbar title when Navigating among Home Navigation
     */
    /*private fun setupActionBarWithNavController(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ -> toolbar.title = destination.label }
    }*/

    private fun openUserDetails(user: User) {
        val intent = Intent(this, UserLocationActivity::class.java).apply {
            putExtra(UserLocationActivity.EXTRA_USER_ADDRESS,
                    "${user.address.suite}, ${user.address.street}, ${user.address.city}-${user.address.zipcode}")
            putExtra(UserLocationActivity.EXTRA_USER_LOCATION,
                    LatLng(user.address.geo.lat.toDouble(), user.address.geo.lng.toDouble()))
        }
        startActivity(intent)
    }

    fun obtainUsersViewModel(): UsersViewModel = obtainViewModel(UsersViewModel::class.java)

    fun obtainFavoritesViewModel(): FavoritesViewModel = obtainViewModel(FavoritesViewModel::class.java)

}

package com.team.pizzabuilder.presentation.navigate

import androidx.fragment.app.FragmentManager
import com.team.pizzabuilder.R
import com.team.pizzabuilder.presentation.general.fragments.MainFragment
import com.team.pizzabuilder.presentation.general.fragments.UserProfileFragment
import com.team.pizzabuilder.presentation.owner.fragments.CreatePizzaFragment

class NavigateHelperFragments(private val fragmentManager: FragmentManager) {

    // navigate navButton
    fun navigateToMainFragment() {
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MainFragment.newInstance())
            .addToBackStack(MainFragment.TAG_FRAGMENT)
            .commit()
    }

    fun navigateToUserProfileFragment() {
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, UserProfileFragment.newInstance())
            .addToBackStack(UserProfileFragment.TAG_FRAGMENT)
            .commit()
    }
    // navigate navButton

    // navigate admin functionality
    fun navigateToCreatePizzaFragment() {
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CreatePizzaFragment.newInstance())
            .commit()
    }

    fun navigateFromCreatePizzaInUserProfile() {
        fragmentManager.popBackStack()
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, UserProfileFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    fun navigateToMainFragmentAfterSuccessfulCreatePizza() {
        fragmentManager.popBackStack()
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MainFragment.newInstance())
            .addToBackStack(MainFragment.TAG_FRAGMENT)
            .commit()
    }
    // navigate admin functionality

    fun navigateToMainFragmentAfterUserProfile() {
        fragmentManager.popBackStack()
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MainFragment.newInstance())
            .addToBackStack(MainFragment.TAG_FRAGMENT)
            .commit()
    }
}
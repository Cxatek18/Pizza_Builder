package com.team.pizzabuilder.presentation.general.activitys

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.team.pizzabuilder.R
import com.team.pizzabuilder.databinding.ActivityMainBinding
import com.team.pizzabuilder.presentation.navigate.NavigateHelperFragments

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val navigateHelperFragments = NavigateHelperFragments(
        supportFragmentManager
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navigateHelperFragments.navigateToMainFragment()
        onClickItemMenu()
    }

    private fun onClickItemMenu() {
        binding.navigationMenu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_page -> {
                    navigateHelperFragments.navigateToMainFragment()
                }

                R.id.user_info_page -> {
                    navigateHelperFragments.navigateToUserProfileFragment()
                }

                else -> {

                }
            }
            true
        }
    }

    companion object {
        fun newInstance(context: SplashScreen): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}
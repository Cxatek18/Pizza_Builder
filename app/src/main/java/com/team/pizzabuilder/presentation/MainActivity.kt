package com.team.pizzabuilder.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.team.pizzabuilder.R
import com.team.pizzabuilder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainConstraintLayout, MainFragment.newInstance())
            .commit()
    }

    companion object {
        fun newInstance(context: SplashScreen): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}
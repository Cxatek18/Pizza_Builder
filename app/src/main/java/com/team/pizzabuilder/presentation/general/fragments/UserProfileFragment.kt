package com.team.pizzabuilder.presentation.general.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.team.pizzabuilder.R
import com.team.pizzabuilder.databinding.FragmentUserProfileBinding
import com.team.pizzabuilder.presentation.navigate.NavigateHelperFragments

class UserProfileFragment : Fragment() {

    private var _binding: FragmentUserProfileBinding? = null
    private val binding: FragmentUserProfileBinding
        get() = _binding ?: throw RuntimeException("FragmentUserProfileBinding is null")

    private lateinit var navigateHelperFragments: NavigateHelperFragments

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigateHelperFragments = NavigateHelperFragments(
            requireActivity().supportFragmentManager
        )
        val callback: OnBackPressedCallback = object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateHelperFragments.navigateToMainFragmentAfterUserProfile()
                requireActivity()
                    .findViewById<BottomNavigationView>(R.id.navigation_menu)
                    .selectedItemId = R.id.home_page
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCLickBtnCreatePizza()
    }

    private fun onCLickBtnCreatePizza() {
        binding.btnAddPizza.setOnClickListener {
            navigateHelperFragments.navigateToCreatePizzaFragment()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        const val TAG_FRAGMENT = "user_profile"

        fun newInstance(): UserProfileFragment {
            return UserProfileFragment()
        }
    }
}
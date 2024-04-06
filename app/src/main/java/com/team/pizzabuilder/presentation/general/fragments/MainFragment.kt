package com.team.pizzabuilder.presentation.general.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.LottieDrawable
import com.team.pizzabuilder.databinding.FragmentMainBinding
import com.team.pizzabuilder.presentation.general.adapters.PizzaAdapter
import java.lang.Thread.sleep

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding is null")

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory
                .getInstance(requireActivity().application)
        )[MainViewModel::class.java]
    }

    private lateinit var adapter: PizzaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = PizzaAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        binding.rvPizzaList.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.listPizza.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        requireActivity().finish()
    }

    companion object {

        const val TAG_FRAGMENT = "main_fragment"

        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}
package com.team.pizzabuilder.presentation.general.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.team.pizzabuilder.app.App
import com.team.pizzabuilder.databinding.FragmentMainBinding
import com.team.pizzabuilder.presentation.general.adapters.PizzaAdapter
import com.team.pizzabuilder.presentation.general.view_models.MainViewModel
import com.team.pizzabuilder.presentation.general.view_models.MainViewModelFactory
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var vmFactory: MainViewModelFactory

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding is null")

    private lateinit var viewModel: MainViewModel

    private lateinit var adapter: PizzaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, vmFactory)[MainViewModel::class.java]
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
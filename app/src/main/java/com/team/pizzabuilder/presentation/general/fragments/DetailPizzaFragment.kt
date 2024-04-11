package com.team.pizzabuilder.presentation.general.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.team.pizzabuilder.app.App
import com.team.pizzabuilder.databinding.FragmentDetailPizzaBinding
import com.team.pizzabuilder.presentation.general.view_models.DetailPizzaViewModel
import com.team.pizzabuilder.presentation.general.view_models.DetailPizzaViewModelFactory
import javax.inject.Inject

class DetailPizzaFragment : Fragment() {

    @Inject
    lateinit var vmFactory: DetailPizzaViewModelFactory

    private var _binding: FragmentDetailPizzaBinding? = null
    private val binding: FragmentDetailPizzaBinding
        get() = _binding ?: throw RuntimeException("FragmentDetailPizzaBinding is null")

    private var pizzaId: Int = -1

    private lateinit var viewModel: DetailPizzaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
        (requireActivity().applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, vmFactory)[DetailPizzaViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailPizzaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPizza(pizzaId = pizzaId)
        observeViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun observeViewModel() {
        viewModel.pizza.observe(viewLifecycleOwner) {
            with(binding) {
                progressBar.visibility = View.GONE
                Glide.with(requireActivity().applicationContext)
                    .load(it.imageUrl)
                    .into(imagePizza)
                titlePizza.text = it.name
                descriptionPizza.text = it.description
                pizzaPrice.text = String.format("Цена: %s руб.", it.price)
            }
        }
    }

    private fun parseArgs() {
        val args = requireArguments()
        if (!args.containsKey(EXTRA_ID_PIZZA)) {
            throw RuntimeException(TEXT_ERROR_NO_ARGS)
        }
        pizzaId = args.getInt(EXTRA_ID_PIZZA)
    }

    companion object {
        const val TAG_FRAGMENT = "detail_fragment"
        private const val EXTRA_ID_PIZZA = "pizza_id"
        private const val TEXT_ERROR_NO_ARGS = "sorry is args equal null"

        fun newInstance(pizzaId: Int): DetailPizzaFragment {
            return DetailPizzaFragment().apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_ID_PIZZA, pizzaId)
                }
            }
        }
    }
}
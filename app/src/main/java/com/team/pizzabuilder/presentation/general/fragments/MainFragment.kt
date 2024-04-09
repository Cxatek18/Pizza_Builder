package com.team.pizzabuilder.presentation.general.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.team.pizzabuilder.R
import com.team.pizzabuilder.app.App
import com.team.pizzabuilder.databinding.FragmentMainBinding
import com.team.pizzabuilder.domain.general.models.Pizza
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
        setUpClickListenerDeletePizza()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        requireActivity().finish()
    }

    private fun observeViewModel() {
        viewModel.listPizza.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.progressBar.visibility = View.GONE
        }

        viewModel.isDeleted.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(
                    requireContext(),
                    TEXT_SUCCESS_DELETED,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setUpClickListenerDeletePizza() {
        adapter.onCLickDeletePizza = {
            createDeletedDialog(it)
        }
    }

    private fun createDeletedDialog(pizza: Pizza) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_alert_dialog_delete_pizza)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(true)
        val btnDelete = dialog.findViewById<AppCompatButton>(R.id.btn_true_delete)
        val btnNotDelete = dialog.findViewById<AppCompatButton>(R.id.btn_false_delete)
        onClickListenCloseDeleteDialog(btnNotDelete, dialog)
        onClickListenDeletePizza(btnDelete, pizza, dialog)
        dialog.show()
    }

    private fun onClickListenCloseDeleteDialog(btnNotDelete: AppCompatButton, dialog: Dialog) {
        btnNotDelete.setOnClickListener {
            dialog.cancel()
        }
    }

    private fun onClickListenDeletePizza(
        btnDelete: AppCompatButton,
        pizza: Pizza,
        dialog: Dialog
    ) {
        btnDelete.setOnClickListener {
            viewModel.deletePizza(pizza)
            dialog.cancel()
        }
    }

    companion object {

        const val TAG_FRAGMENT = "main_fragment"
        private const val TEXT_SUCCESS_DELETED = "Вы успешно удалили пиццу"

        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}
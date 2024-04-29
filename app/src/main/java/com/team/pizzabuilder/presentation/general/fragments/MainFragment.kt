package com.team.pizzabuilder.presentation.general.fragments

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
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
import com.team.pizzabuilder.presentation.navigate.NavigateHelperFragments
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var vmFactory: MainViewModelFactory

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding is null")

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: PizzaAdapter
    private lateinit var navigateHelperFragments: NavigateHelperFragments

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigateHelperFragments = NavigateHelperFragments(
            requireActivity().supportFragmentManager
        )
    }

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
        onCLickSearchBtn()
        onCLickCloseSearchField()
        searchPizzaListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        requireActivity().finish()
    }

    fun View.hideMyKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
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

    private fun searchPizzaListener() {
        binding.etSearchPizza.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchPizza("%${s.toString()}%")
                    .observe(viewLifecycleOwner) {
                        adapter.submitList(it)
                    }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun setUpClickListenerDeletePizza() {
        adapter.onCLickDeletePizza = {
            createDeletedDialog(it)
        }
        adapter.onClickUpdatePizza = {
            onCLickListenUpdatePizza(it)
        }
        adapter.onClickDetailPizza = {
            onCLickListenDetailPizza(it)
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

    private fun onCLickSearchBtn() {
        with(binding) {
            btnSearch.setOnClickListener {
                logoHeader.visibility = View.GONE
                titleHeader.visibility = View.GONE
                btnSearch.visibility = View.GONE
                etSearchPizza.visibility = View.VISIBLE
                btnCloseSearchField.visibility = View.VISIBLE
                etSearchPizza.requestFocus()
            }
        }
    }

    private fun onCLickCloseSearchField() {
        with(binding) {
            btnCloseSearchField.setOnClickListener {
                logoHeader.visibility = View.VISIBLE
                titleHeader.visibility = View.VISIBLE
                btnSearch.visibility = View.VISIBLE
                etSearchPizza.visibility = View.GONE
                btnCloseSearchField.visibility = View.GONE
                etSearchPizza.text.clear()
                it.hideMyKeyboard()
            }
        }
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

    private fun onCLickListenUpdatePizza(
        pizza: Pizza
    ) {
        navigateHelperFragments.navigateFromMainFragmentToUpdateFragment(pizza)
    }

    private fun onCLickListenDetailPizza(
        pizzaId: Int
    ) {
        navigateHelperFragments.navigateFromMainFragmentToPizzaDetailFragment(pizzaId)
    }

    companion object {

        const val TAG_FRAGMENT = "main_fragment"
        private const val TEXT_SUCCESS_DELETED = "Вы успешно удалили пиццу"

        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}
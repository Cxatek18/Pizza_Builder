package com.team.pizzabuilder.presentation.owner.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.team.pizzabuilder.R
import com.team.pizzabuilder.app.App
import com.team.pizzabuilder.databinding.FragmentUpdatePizzaBinding
import com.team.pizzabuilder.domain.general.models.Pizza
import com.team.pizzabuilder.presentation.navigate.NavigateHelperFragments
import com.team.pizzabuilder.presentation.owner.view_models.UpdatePizzaViewModel
import com.team.pizzabuilder.presentation.owner.view_models.UpdatePizzaViewModelFactory
import javax.inject.Inject

class UpdatePizzaFragment : Fragment() {

    @Inject
    lateinit var vmFactory: UpdatePizzaViewModelFactory

    private var _binding: FragmentUpdatePizzaBinding? = null
    private val binding: FragmentUpdatePizzaBinding
        get() = _binding ?: throw RuntimeException("FragmentUpdatePizzaBinding is null")

    private val launcher: ActivityResultLauncher<PickVisualMediaRequest> =
        this.registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) {
            pizzaImageUri = if (it != null) {
                setPhotoNoError(it)
                it
            } else {
                pizza.imageUrl.toUri()
            }
        }

    private var pizzaImageUri: Uri? = null
    private lateinit var pizza: Pizza
    private lateinit var viewModel: UpdatePizzaViewModel
    private lateinit var navigateHelperFragments: NavigateHelperFragments
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigateHelperFragments = NavigateHelperFragments(
            requireActivity().supportFragmentManager
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, vmFactory)[UpdatePizzaViewModel::class.java]
        setWindowInputMode()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdatePizzaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkVisibleKeyboard()
        parseArgs()
        fillingAllFields()
        checkMediaImagePermission()
        registerPermissionListener()
        viewModel.getPizza(pizza.id)
        onClickButtonUpdatePizza()
        observeViewModel()
        onClickDownloadImage()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun onClickButtonUpdatePizza() {
        binding.btnUpdatePizza.setOnClickListener {
            updatePizza()
        }
    }

    private fun updatePizza() {
        with(binding) {
            val name = etNamePizza.text
            val description = etDescriptionPizza.text
            val price = etPricePizza.text
            viewModel.updatePizza(
                name = name.toString().replaceFirstChar {
                    it.uppercase()
                },
                description = description.toString().replaceFirstChar {
                    it.uppercase()
                },
                price = price.toString(),
                image = pizzaImageUri.toString()
            )
        }
    }

    private fun observeViewModel() {
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            if (it == false) {
                binding.errorNamePizza.visibility = View.VISIBLE
                binding.textErrorName.text = ERROR_TEXT_NAME
            } else {
                binding.errorNamePizza.visibility = View.GONE
            }
        }
        viewModel.errorInputDescription.observe(viewLifecycleOwner) {
            if (it == false) {
                binding.errorDescriptionPizza.visibility = View.VISIBLE
                binding.textErrorDescription.text = ERROR_TEXT_DESCRIPTION
            } else {
                binding.errorDescriptionPizza.visibility = View.GONE
            }
        }
        viewModel.errorInputPrice.observe(viewLifecycleOwner) {
            if (it == false) {
                binding.errorPricePizza.visibility = View.VISIBLE
                binding.textErrorPrice.text = ERROR_TEXT_PRICE
            } else {
                binding.errorPricePizza.visibility = View.GONE
            }
        }
        viewModel.closedScreen.observe(viewLifecycleOwner) {
            navigateHelperFragments.navigateFromUpdatePizzaToMainFragment()
        }
    }

    private fun parseArgs() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(EXTRA_PIZZA, Pizza::class.java)?.let {
                pizza = it
            }
        } else {
            requireArguments().getParcelable<Pizza>(EXTRA_PIZZA)?.let {
                pizza = it
            }
        }
    }

    private fun fillingAllFields() {
        with(binding) {
            textTitleFragment.text = String.format("Обновление пиццы - %s", pizza.name)
            etNamePizza.setText(pizza.name)
            etDescriptionPizza.setText(pizza.description)
            etPricePizza.setText(pizza.price.toString())
            Glide.with(requireActivity().applicationContext)
                .load(pizza.imageUrl)
                .into(downloadImage)
            pizzaImageUri = pizza.imageUrl.toUri()
        }
    }

    private fun onClickDownloadImage() {
        binding.btnDownloadImagePizza.setOnClickListener {
            onClickDownloadImagePizza()
        }
    }

    private fun onClickDownloadImagePizza() {
        launcher.launch(
            PickVisualMediaRequest.Builder()
                .setMediaType(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                ).build()
        )
    }

    private fun setPhotoNoError(uri: Uri?) {
        pizzaImageUri = uri
        if (uri == null) {
            pizzaImageUri = pizza.imageUrl.toUri()
        }
        with(binding) {
            downloadImage.visibility = View.VISIBLE
            textImageDownload.visibility = View.VISIBLE
            Glide.with(requireActivity().applicationContext)
                .load(pizzaImageUri)
                .into(downloadImage)
        }
    }

    private fun checkMediaImagePermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED -> {
            }

            else -> {
                permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            }
        }
    }

    private fun registerPermissionListener() {
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            if (it) {
                Toast.makeText(
                    requireContext(),
                    TEXT_IMAGE_MEDIA_TRUE_PERMISSION,
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    TEXT_IMAGE_MEDIA_FALSE_PERMISSION,
                    Toast.LENGTH_LONG
                ).show()
                navigateHelperFragments.navigateFromUpdatePizzaToMainFragment()
            }
        }
    }

    private fun checkVisibleKeyboard() {
        val frame = binding.frameUpdatePizzaFragment
        frame.viewTreeObserver.addOnGlobalLayoutListener {
            val rec = Rect()
            frame.getWindowVisibleDisplayFrame(rec)
            val screenHeight = frame.rootView.height
            val keypadHeight = screenHeight - rec.bottom
            if (keypadHeight > screenHeight * 0.15) {
                setVisibilityNavigationMenu(true)
            } else {
                setVisibilityNavigationMenu(false)
            }
        }
    }

    private fun setVisibilityNavigationMenu(hasFocus: Boolean) {
        val navigationMenu = activity
            ?.findViewById<BottomNavigationView>(R.id.navigation_menu)
        if (hasFocus) {
            navigationMenu?.visibility = View.GONE
        } else {
            navigationMenu?.visibility = View.VISIBLE
        }
    }

    private fun setWindowInputMode() {
        requireActivity()
            .window
            .setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
            )
    }

    companion object {
        const val TAG_FRAGMENT = "update_pizza"
        private const val EXTRA_PIZZA = "pizza_item"
        private const val TEXT_ERROR_DOWNLOAD_IMAGE =
            "Произошла ошибка, повторите ещё раз. Если не получиться напишите нам."
        private const val ERROR_TEXT_NAME = "Название не может быть пустым"
        private const val ERROR_TEXT_DESCRIPTION = "Описание не может быть пустым"
        private const val ERROR_TEXT_PRICE = "Цена должна быть больше 0"
        private const val ERROR_TEXT_IMAGE = "Фото должно быть загруженно"
        private const val TEXT_IMAGE_MEDIA_TRUE_PERMISSION = "Теперь вы можете загружать фото"
        private const val TEXT_IMAGE_MEDIA_FALSE_PERMISSION =
            "Выдайте разрешение на доступ к вашим медиафайлам"

        fun newInstance(pizza: Pizza): UpdatePizzaFragment {
            return UpdatePizzaFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_PIZZA, pizza)
                }
            }
        }
    }
}
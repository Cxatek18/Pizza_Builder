package com.team.pizzabuilder.presentation.owner

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.team.pizzabuilder.R
import com.team.pizzabuilder.databinding.FragmentCreatePizzaBinding
import com.team.pizzabuilder.presentation.navigate.NavigateHelperFragments

class CreatePizzaFragment : Fragment() {

    private var _binding: FragmentCreatePizzaBinding? = null
    private val binding: FragmentCreatePizzaBinding
        get() = _binding ?: throw RuntimeException("FragmentCreatePizzaBinding is null")

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory
                .getInstance(requireActivity().application)
        )[CreatePizzaViewModel::class.java]
    }

    private val launcher: ActivityResultLauncher<PickVisualMediaRequest> =
        this.registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) {
            if (it != null) {
                setPhotoNoError(it)
            } else {
                setPhotoError()
            }
        }

    private var pizzaImageUrl: Uri? = null
    private lateinit var navigateHelperFragments: NavigateHelperFragments
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigateHelperFragments = NavigateHelperFragments(
            requireActivity().supportFragmentManager
        )
        val callback: OnBackPressedCallback = object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateHelperFragments.navigateFromCreatePizzaInUserProfile()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWindowInputMode()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatePizzaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkVisibleKeyboard()

        // Придумать как сократить и убрать лишнее
        registerPermissionListener()
        checkMediaImagePermission()
        // Придумать как сократить и убрать лишнее
        onClickButtonCreatePizza()
        onClickDownloadImage()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding == null
    }

    private fun checkVisibleKeyboard() {
        val frame = binding.frameCreatePizzaFragment
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

    private fun checkMediaImagePermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED -> {}
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
                navigateHelperFragments.navigateFromCreatePizzaInUserProfile()
            }
        }
    }

    private fun onClickButtonCreatePizza() {
        binding.btnCreatePizza.setOnClickListener {
            createPizza()
            observeViewModel()
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

    private fun createPizza() {
        with(binding) {
            val name = etNamePizza.text
            val description = etDescriptionPizza.text
            val price = etPricePizza.text
            viewModel.createPizza(
                name = name.toString().replaceFirstChar {
                    it.uppercase()
                },
                description = description.toString().replaceFirstChar {
                    it.uppercase()
                },
                imageUrl = pizzaImageUrl?.toString() ?: "",
                price = price.toString()
            )
        }

    }

    private fun setVisibilityNavigationMenu(hasFocus: Boolean) {
        val navigationMenu = requireActivity()
            .findViewById<BottomNavigationView>(R.id.navigation_menu)
        if (hasFocus) {
            navigationMenu.visibility = View.GONE
        } else {
            navigationMenu.visibility = View.VISIBLE
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
        viewModel.errorInputImageUrl.observe(viewLifecycleOwner) {
            if (it == false) {
                binding.errorDownloadImagePizza.visibility = View.VISIBLE
                binding.textErrorDownloadImage.text = ERROR_TEXT_IMAGE
            } else {
                binding.errorDownloadImagePizza.visibility = View.GONE
            }
        }
        viewModel.closedScreen.observe(viewLifecycleOwner) {
            navigateHelperFragments.navigateToMainFragmentAfterSuccessfulCreatePizza()
            requireActivity()
                .findViewById<BottomNavigationView>(R.id.navigation_menu)
                .selectedItemId = R.id.home_page
        }
    }

    private fun setPhotoNoError(uri: Uri?) {
        pizzaImageUrl = uri
        with(binding) {
            textNoImage.visibility = View.GONE
            downloadImage.visibility = View.VISIBLE
            textImageDownload.visibility = View.VISIBLE
            Glide.with(requireActivity().applicationContext)
                .load(pizzaImageUrl)
                .into(downloadImage)
        }
    }

    private fun setPhotoError() {
        Toast.makeText(
            requireContext(),
            TEXT_ERROR_DOWNLOAD_IMAGE,
            Toast.LENGTH_LONG
        ).show()
        with(binding) {
            textNoImage.visibility = View.VISIBLE
            downloadImage.visibility = View.GONE
            textImageDownload.visibility = View.GONE
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
        private const val TEXT_ERROR_DOWNLOAD_IMAGE =
            "Произошла ошибка, повторите ещё раз. Если не получиться напишите нам."
        private const val ERROR_TEXT_NAME = "Поле (Имя) не может быть пустым"
        private const val ERROR_TEXT_DESCRIPTION = "Поле (Описание) не может быть пустым"
        private const val ERROR_TEXT_PRICE = "Поле (Цена) должно быть больше 0"
        private const val ERROR_TEXT_IMAGE = "Фото должно быть загруженно"
        private const val TEXT_IMAGE_MEDIA_TRUE_PERMISSION = "Теперь вы можете загружать фото"
        private const val TEXT_IMAGE_MEDIA_FALSE_PERMISSION =
            "Выдайте разрешение на доступ к вашим медиафайлам"
        const val TAG_FRAGMENT = "create_pizza"

        fun newInstance(): CreatePizzaFragment {
            return CreatePizzaFragment()
        }
    }
}
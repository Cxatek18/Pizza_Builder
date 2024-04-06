package com.team.pizzabuilder.presentation.owner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.team.pizzabuilder.data.general.repository.PizzaRepositoryImpl
import com.team.pizzabuilder.domain.general.models.Pizza
import com.team.pizzabuilder.domain.general.usecase.CreatePizzaUseCase
import kotlinx.coroutines.launch

class CreatePizzaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PizzaRepositoryImpl(
        application = application
    )
    private val createPizzaUseCase = CreatePizzaUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputDescription = MutableLiveData<Boolean>()
    val errorInputDescription: LiveData<Boolean>
        get() = _errorInputDescription

    private val _errorInputImageUrl = MutableLiveData<Boolean>()
    val errorInputImageUrl: LiveData<Boolean>
        get() = _errorInputImageUrl

    private val _errorInputPrice = MutableLiveData<Boolean>()
    val errorInputPrice: LiveData<Boolean>
        get() = _errorInputPrice

    private val _closedScreen = MutableLiveData<Unit>()
    val closedScreen: LiveData<Unit>
        get() = _closedScreen

    fun createPizza(name: String?, description: String, imageUrl: String, price: String) {
        val name = parseName(name)
        val description = parseDescription(description)
        val imageUrl = parseImageUrl(imageUrl)
        val price = parsePrice(price)
        if(validateInput(name, description, imageUrl, price)) {
            viewModelScope.launch {
                val pizza = Pizza(
                    name = name,
                    description = description,
                    imageUrl = imageUrl,
                    price = price
                )
                createPizzaUseCase.createPizza(pizza)
            }
            finishWork()
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseDescription(inputDescription: String?): String {
        return inputDescription?.trim() ?: ""
    }

    private fun parseImageUrl(inputImageUrl: String?): String {
        return inputImageUrl?.trim() ?: ""
    }

    private fun parsePrice(inputPrice: String?): Int {
        return try {
            inputPrice?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(
        name: String,
        description: String,
        imageUrl: String,
        price: Int
    ): Boolean {
        var result: Boolean = true
        if (name.isBlank()) {
            _errorInputName.value = false
            result = false
        }else {
            _errorInputName.value = true
        }
        if (description.isBlank()) {
            _errorInputDescription.value = false
            result = false
        } else {
            _errorInputDescription.value = true
        }
        if(imageUrl.isBlank()) {
            _errorInputImageUrl.value = false
            result = false
        } else {
            _errorInputImageUrl.value = true
        }
        if (price <= 0) {
            _errorInputPrice.value = false
            result = false
        } else {
            _errorInputPrice.value = true
        }
        return result
    }

    private fun finishWork() {
        _closedScreen.value = Unit
    }
}
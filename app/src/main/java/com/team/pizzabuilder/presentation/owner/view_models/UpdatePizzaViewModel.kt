package com.team.pizzabuilder.presentation.owner.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.pizzabuilder.domain.general.models.Pizza
import com.team.pizzabuilder.domain.general.usecase.GetPizzaUseCase
import com.team.pizzabuilder.domain.owner.usecase.UpdatePizzaUseCase
import kotlinx.coroutines.launch

class UpdatePizzaViewModel(
    val updatePizzaUseCase: UpdatePizzaUseCase,
    val getPizzaUseCase: GetPizzaUseCase
) : ViewModel() {

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputDescription = MutableLiveData<Boolean>()
    val errorInputDescription: LiveData<Boolean>
        get() = _errorInputDescription

    private val _errorInputPrice = MutableLiveData<Boolean>()
    val errorInputPrice: LiveData<Boolean>
        get() = _errorInputPrice

    private val _closedScreen = MutableLiveData<Unit>()
    val closedScreen: LiveData<Unit>
        get() = _closedScreen

    private val _pizza = MutableLiveData<Pizza>()
    val pizza: LiveData<Pizza>
        get() = _pizza

    fun getPizza(pizzaId: Int) {
        viewModelScope.launch {
            val pizza = getPizzaUseCase.getPizza(pizzaId)
            _pizza.value = pizza
        }
    }

    fun updatePizza(
        name: String,
        description: String,
        price: String,
        image: String
    ) {
        val name = parseName(name)
        val description = parseDescription(description)
        val price = parsePrice(price)
        val image = parseImageUrl(image)
        if (validateField(name, description, price)) {
            _pizza.value?.let {
                viewModelScope.launch {
                    val pizza = it.copy(
                        name = name,
                        description = description,
                        price = price,
                        imageUrl = image
                    )
                    updatePizzaUseCase.updatePizza(pizza)
                }
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

    private fun validateField(
        name: String,
        description: String,
        price: Int,
    ): Boolean {
        var result: Boolean = true
        if (name.isBlank()) {
            _errorInputName.value = false
            result = false
        } else {
            _errorInputName.value = true
        }
        if (description.isBlank()) {
            _errorInputDescription.value = false
            result = false
        } else {
            _errorInputDescription.value = true
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
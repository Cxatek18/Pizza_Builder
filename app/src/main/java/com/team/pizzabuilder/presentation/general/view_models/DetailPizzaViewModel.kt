package com.team.pizzabuilder.presentation.general.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.pizzabuilder.domain.general.models.Pizza
import com.team.pizzabuilder.domain.general.usecase.GetPizzaUseCase
import kotlinx.coroutines.launch

class DetailPizzaViewModel(
    val getPizzaUseCase: GetPizzaUseCase
) : ViewModel() {

    private val _pizza = MutableLiveData<Pizza>()
    val pizza: LiveData<Pizza>
        get() = _pizza

    fun getPizza(pizzaId: Int) {
        viewModelScope.launch {
            val pizza = getPizzaUseCase.getPizza(pizzaId)
            _pizza.value = pizza
        }
    }
}
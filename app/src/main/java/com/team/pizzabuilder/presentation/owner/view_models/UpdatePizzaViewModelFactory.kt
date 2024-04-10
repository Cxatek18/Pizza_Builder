package com.team.pizzabuilder.presentation.owner.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.team.pizzabuilder.domain.general.usecase.GetPizzaUseCase
import com.team.pizzabuilder.domain.owner.usecase.UpdatePizzaUseCase

class UpdatePizzaViewModelFactory(
    val updatePizzaUseCase: UpdatePizzaUseCase,
    val getPizzaUseCase: GetPizzaUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UpdatePizzaViewModel(
            updatePizzaUseCase = updatePizzaUseCase,
            getPizzaUseCase = getPizzaUseCase
        ) as T
    }
}
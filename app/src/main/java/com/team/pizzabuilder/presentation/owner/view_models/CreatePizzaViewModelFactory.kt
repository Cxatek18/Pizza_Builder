package com.team.pizzabuilder.presentation.owner.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.team.pizzabuilder.domain.general.usecase.CreatePizzaUseCase

class CreatePizzaViewModelFactory(
    val createPizzaUseCase: CreatePizzaUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreatePizzaViewModel(
            createPizzaUseCase = createPizzaUseCase
        ) as T
    }
}
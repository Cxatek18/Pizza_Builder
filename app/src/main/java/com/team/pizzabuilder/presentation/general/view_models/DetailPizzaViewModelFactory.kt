package com.team.pizzabuilder.presentation.general.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.team.pizzabuilder.domain.general.usecase.GetPizzaUseCase

class DetailPizzaViewModelFactory(
    val getPizzaUseCase: GetPizzaUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailPizzaViewModel(
            getPizzaUseCase = getPizzaUseCase
        ) as T
    }
}
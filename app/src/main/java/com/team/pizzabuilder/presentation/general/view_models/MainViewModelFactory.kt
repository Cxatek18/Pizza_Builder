package com.team.pizzabuilder.presentation.general.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.team.pizzabuilder.domain.general.usecase.GetListPizzaUseCase
import com.team.pizzabuilder.domain.owner.usecase.DeletePizzaUseCase

class MainViewModelFactory(
    val getListPizzaUseCase: GetListPizzaUseCase,
    val deletePizzaUseCase: DeletePizzaUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            getListPizzaUseCase = getListPizzaUseCase,
            deletePizzaUseCase = deletePizzaUseCase
        ) as T
    }
}
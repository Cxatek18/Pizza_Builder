package com.team.pizzabuilder.presentation.general.view_models


import androidx.lifecycle.ViewModel
import com.team.pizzabuilder.domain.general.usecase.GetListPizzaUseCase

class MainViewModel(
    val getListPizzaUseCase: GetListPizzaUseCase
) : ViewModel() {

    val listPizza = getListPizzaUseCase.getListPizza()
}
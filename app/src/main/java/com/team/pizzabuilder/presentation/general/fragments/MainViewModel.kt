package com.team.pizzabuilder.presentation.general.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.team.pizzabuilder.data.general.repository.PizzaRepositoryImpl
import com.team.pizzabuilder.domain.general.usecase.GetListPizzaUseCase

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PizzaRepositoryImpl(
        application = application
    )

    private val getListPizzaUseCase = GetListPizzaUseCase(repository)

    val listPizza = getListPizzaUseCase.getListPizza()
}
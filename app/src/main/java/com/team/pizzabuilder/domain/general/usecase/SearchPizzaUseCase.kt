package com.team.pizzabuilder.domain.general.usecase

import androidx.lifecycle.LiveData
import com.team.pizzabuilder.domain.general.models.Pizza
import com.team.pizzabuilder.domain.general.repository.PizzaRepository

class SearchPizzaUseCase(private val repository: PizzaRepository) {

    fun searchPizza(searchParameter: String): LiveData<List<Pizza>> {
        return repository.searchPizza(searchParameter)
    }
}
package com.team.pizzabuilder.domain.general.usecase

import com.team.pizzabuilder.domain.general.models.Pizza
import com.team.pizzabuilder.domain.general.repository.PizzaRepository

class GetPizzaUseCase(private val repository: PizzaRepository) {

    suspend fun getPizza(id: Int): Pizza {
        return repository.getPizza(id)
    }
}
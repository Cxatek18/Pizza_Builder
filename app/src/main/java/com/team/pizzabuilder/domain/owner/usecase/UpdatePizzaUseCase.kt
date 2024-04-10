package com.team.pizzabuilder.domain.owner.usecase

import com.team.pizzabuilder.domain.general.models.Pizza
import com.team.pizzabuilder.domain.general.repository.PizzaRepository

class UpdatePizzaUseCase(private val repository: PizzaRepository) {

    suspend fun updatePizza(pizza: Pizza) {
        repository.updatePizza(pizza)
    }
}
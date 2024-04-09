package com.team.pizzabuilder.domain.owner.usecase

import com.team.pizzabuilder.domain.general.models.Pizza
import com.team.pizzabuilder.domain.general.repository.PizzaRepository

class CreatePizzaUseCase(private val repository: PizzaRepository) {

    suspend fun createPizza(pizza: Pizza) {
        repository.createPizza(pizza)
    }
}
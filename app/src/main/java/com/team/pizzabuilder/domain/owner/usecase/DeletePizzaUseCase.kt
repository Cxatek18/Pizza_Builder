package com.team.pizzabuilder.domain.owner.usecase

import com.team.pizzabuilder.domain.general.models.Pizza
import com.team.pizzabuilder.domain.general.repository.PizzaRepository

class DeletePizzaUseCase(private val repository: PizzaRepository) {

    suspend fun deletePizza(pizza: Pizza) {
        repository.deletePizza(pizza)
    }
}
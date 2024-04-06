package com.team.pizzabuilder.domain.general.usecase

import androidx.lifecycle.LiveData
import com.team.pizzabuilder.domain.general.models.Pizza
import com.team.pizzabuilder.domain.general.repository.PizzaRepository

class GetListPizzaUseCase(private val repository: PizzaRepository) {

    fun getListPizza(): LiveData<List<Pizza>> {
        return repository.getListPizza()
    }
}
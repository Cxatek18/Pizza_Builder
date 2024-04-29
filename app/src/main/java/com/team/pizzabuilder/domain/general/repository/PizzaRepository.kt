package com.team.pizzabuilder.domain.general.repository

import androidx.lifecycle.LiveData
import com.team.pizzabuilder.domain.general.models.Pizza

interface PizzaRepository {

    suspend fun getPizza(id: Int): Pizza

    fun getListPizza(): LiveData<List<Pizza>>

    suspend fun createPizza(pizza: Pizza)

    suspend fun deletePizza(pizza: Pizza)

    suspend fun updatePizza(pizza: Pizza)

    fun searchPizza(searchParameter: String): LiveData<List<Pizza>>
}
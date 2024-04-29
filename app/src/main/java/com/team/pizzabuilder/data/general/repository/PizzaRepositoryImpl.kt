package com.team.pizzabuilder.data.general.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.team.pizzabuilder.data.general.database.dao.PizzaDao
import com.team.pizzabuilder.data.general.mapper.PizzaMapper
import com.team.pizzabuilder.domain.general.models.Pizza
import com.team.pizzabuilder.domain.general.repository.PizzaRepository

class PizzaRepositoryImpl(
    val mapper: PizzaMapper,
    val pizzaDao: PizzaDao
) : PizzaRepository {

    override suspend fun getPizza(id: Int): Pizza {
        val pizza = pizzaDao.getPizza(id)
        return mapper.mapDbModelToEntity(pizza)
    }

    override fun getListPizza(): LiveData<List<Pizza>> = pizzaDao.getListPizza().map {
        mapper.mapListDbModelToListEntity(it)
    }

    override suspend fun createPizza(pizza: Pizza) {
        pizzaDao.createPizza(mapper.mapEntityToDbModel(pizza))
    }

    override suspend fun deletePizza(pizza: Pizza) {
        pizzaDao.deletePizza(pizza.id)
    }

    override suspend fun updatePizza(pizza: Pizza) {
        pizzaDao.updatePizza(mapper.mapEntityToDbModel(pizza))
    }

    override fun searchPizza(searchParameter: String): LiveData<List<Pizza>> = pizzaDao
        .searchPizza(searchParameter).map {
            mapper.mapListDbModelToListEntity(it)
        }
}
package com.team.pizzabuilder.data.general.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.team.pizzabuilder.data.general.database.AppDatabase
import com.team.pizzabuilder.data.general.database.dao.PizzaDao
import com.team.pizzabuilder.data.general.mapper.PizzaMapper
import com.team.pizzabuilder.domain.general.models.Pizza
import com.team.pizzabuilder.domain.general.repository.PizzaRepository

class PizzaRepositoryImpl(
    private val application: Application,
) : PizzaRepository {

    private val mapper = PizzaMapper()

    private val pizzaDao: PizzaDao = AppDatabase.getInstance(application).pizzaDao()
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
}
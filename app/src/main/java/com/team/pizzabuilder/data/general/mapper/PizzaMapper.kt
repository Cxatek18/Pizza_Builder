package com.team.pizzabuilder.data.general.mapper

import com.team.pizzabuilder.data.general.database.PizzaDbModel
import com.team.pizzabuilder.domain.general.models.Pizza

class PizzaMapper {

    fun mapEntityToDbModel(pizza: Pizza): PizzaDbModel {
        return PizzaDbModel(
            id = pizza.id,
            name = pizza.name,
            description = pizza.description,
            imageUrl = pizza.imageUrl,
            price = pizza.price
        )
    }

    fun mapDbModelToEntity(pizzaDbModel: PizzaDbModel): Pizza {
        return Pizza(
            id = pizzaDbModel.id,
            name = pizzaDbModel.name,
            description = pizzaDbModel.description,
            imageUrl = pizzaDbModel.imageUrl,
            price = pizzaDbModel.price
        )
    }

    fun mapListDbModelToListEntity(list: List<PizzaDbModel>) = list.map {
        mapDbModelToEntity(it)
    }
}
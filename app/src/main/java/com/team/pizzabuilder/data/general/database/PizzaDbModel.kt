package com.team.pizzabuilder.data.general.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pizza")
data class PizzaDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val price: Int,
)

package com.team.pizzabuilder.data.general.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.team.pizzabuilder.data.general.database.PizzaDbModel

@Dao
interface PizzaDao {
    @Query("SELECT * FROM pizza")
    fun getListPizza(): LiveData<List<PizzaDbModel>>

    @Query("SELECT * FROM pizza WHERE id == :id")
    suspend fun getPizza(id: Int): PizzaDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createPizza(pizzaDbModel: PizzaDbModel)

    @Query("DELETE FROM pizza WHERE id=:id")
    suspend fun deletePizza(id: Int)
}
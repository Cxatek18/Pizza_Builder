package com.team.pizzabuilder.di.general

import android.content.Context
import com.team.pizzabuilder.data.general.database.AppDatabase
import com.team.pizzabuilder.data.general.database.dao.PizzaDao
import com.team.pizzabuilder.data.general.mapper.PizzaMapper
import com.team.pizzabuilder.data.general.repository.PizzaRepositoryImpl
import com.team.pizzabuilder.domain.general.repository.PizzaRepository
import dagger.Module
import dagger.Provides

@Module
class DataGeneralModule {

    // Mappers
    @Provides
    fun providePizzaMapper(): PizzaMapper {
        return PizzaMapper()
    }
    // Mappers


    // Database
    @Provides
    fun providePizzaDao(context: Context): PizzaDao {
        return AppDatabase.getInstance(context).pizzaDao()
    }
    // Database


    // Repository
    @Provides
    fun providePizzaRepository(
        mapper: PizzaMapper,
        pizzaDao: PizzaDao
    ): PizzaRepository {
        return PizzaRepositoryImpl(
            mapper = mapper,
            pizzaDao = pizzaDao
        )
    }
    // Repository
}
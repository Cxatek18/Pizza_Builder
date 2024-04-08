package com.team.pizzabuilder.di.general

import com.team.pizzabuilder.domain.general.repository.PizzaRepository
import com.team.pizzabuilder.domain.general.usecase.CreatePizzaUseCase
import com.team.pizzabuilder.domain.general.usecase.GetListPizzaUseCase
import com.team.pizzabuilder.domain.general.usecase.GetPizzaUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainGeneralModule {

    // UseCases
    @Provides
    fun provideGetListPizzaUseCase(repository: PizzaRepository): GetListPizzaUseCase {
        return GetListPizzaUseCase(repository)
    }

    @Provides
    fun provideGetPizzaUseCase(repository: PizzaRepository): GetPizzaUseCase {
        return GetPizzaUseCase(repository)
    }

    @Provides
    fun provideCreatePizzaUseCase(repository: PizzaRepository): CreatePizzaUseCase {
        return CreatePizzaUseCase(repository)
    }
    // UseCases
}
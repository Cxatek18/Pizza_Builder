package com.team.pizzabuilder.di.general

import com.team.pizzabuilder.domain.general.repository.PizzaRepository
import com.team.pizzabuilder.domain.general.usecase.GetListPizzaUseCase
import com.team.pizzabuilder.domain.general.usecase.GetPizzaUseCase
import com.team.pizzabuilder.domain.general.usecase.SearchPizzaUseCase
import com.team.pizzabuilder.domain.owner.usecase.CreatePizzaUseCase
import com.team.pizzabuilder.domain.owner.usecase.DeletePizzaUseCase
import com.team.pizzabuilder.domain.owner.usecase.UpdatePizzaUseCase
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

    @Provides
    fun provideDeletePizzaUseCase(repository: PizzaRepository): DeletePizzaUseCase {
        return DeletePizzaUseCase(repository)
    }

    @Provides
    fun provideUpdatePizzaUseCase(repository: PizzaRepository): UpdatePizzaUseCase {
        return UpdatePizzaUseCase(repository)
    }

    @Provides
    fun provideSearchPizzaUseCase(repository: PizzaRepository): SearchPizzaUseCase {
        return SearchPizzaUseCase(repository)
    }
    // UseCases
}
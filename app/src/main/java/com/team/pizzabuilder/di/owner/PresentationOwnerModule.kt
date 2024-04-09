package com.team.pizzabuilder.di.owner

import com.team.pizzabuilder.domain.owner.usecase.CreatePizzaUseCase
import com.team.pizzabuilder.presentation.owner.view_models.CreatePizzaViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class PresentationOwnerModule {

    @Provides
    fun provideCreatePizzaViewModelFactory(
        createPizzaUseCase: CreatePizzaUseCase
    ): CreatePizzaViewModelFactory {
        return CreatePizzaViewModelFactory(
            createPizzaUseCase = createPizzaUseCase
        )
    }
}